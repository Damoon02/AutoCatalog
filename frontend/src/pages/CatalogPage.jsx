import { useEffect, useMemo, useState } from "react";

import Navbar from "../components/layout/Navbar";
import SearchBar from "../components/catalog/SearchBar";
import BrandList from "../components/catalog/BrandList";
import ModelList from "../components/catalog/ModelList";
import VersionList from "../components/catalog/VersionList";
import VehicleDetail from "../components/catalog/VehicleDetail";

import Loader from "../components/ui/Loader";
import EmptyState from "../components/ui/EmptyState";
import Modal from "../components/ui/Modal";

import AdminPanel from "../components/admin/AdminPanel";
import MarcaForm from "../components/admin/MarcaForm";
import ModeloForm from "../components/admin/ModeloForm";
import VersionForm from "../components/admin/VersionForm";
import LoginForm from "../components/auth/LoginForm";

import {
  getMarcas,
  createMarca,
  updateMarca,
  deleteMarca,
} from "../services/marcaService";

import {
  getModelosByMarca,
  createModelo,
  updateModelo,
  deleteModelo,
} from "../services/modeloService";

import {
  getVersionesByModelo,
  createVersion,
  updateVersion,
  deleteVersion,
} from "../services/versionService";

import {
  login,
  logout,
  isAuthenticated,
  getUserRole,
} from "../services/authService";

import {
  filterBySearch,
  getBrandName,
  getModelName,
  getVersionName,
} from "../utils/catalogUtils";

function CatalogPage() {
  const [brands, setBrands] = useState([]);
  const [models, setModels] = useState([]);
  const [versions, setVersions] = useState([]);

  const [selectedBrand, setSelectedBrand] = useState(null);
  const [selectedModel, setSelectedModel] = useState(null);
  const [selectedVersion, setSelectedVersion] = useState(null);

  const [searchTerm, setSearchTerm] = useState("");

  const [loadingBrands, setLoadingBrands] = useState(true);
  const [loadingModels, setLoadingModels] = useState(false);
  const [loadingVersions, setLoadingVersions] = useState(false);
  const [saving, setSaving] = useState(false);

  const [error, setError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const [logged, setLogged] = useState(isAuthenticated());
  const [role, setRole] = useState(getUserRole() || "USER");
  const [username, setUsername] = useState(
    localStorage.getItem("username") || ""
  );

  const [modalType, setModalType] = useState(null);
  // brand | model | version | login | null

  const [modalMode, setModalMode] = useState("create");
  // create | edit

  useEffect(() => {
    loadBrands();
  }, []);

  async function loadBrands() {
    try {
      setLoadingBrands(true);
      setError("");

      const data = await getMarcas();
      setBrands(Array.isArray(data) ? data : []);
    } catch (err) {
      setError(err.message || "No se pudieron cargar las marcas.");
    } finally {
      setLoadingBrands(false);
    }
  }

  async function reloadModelsForBrand(brandId, preserveModelId = null) {
    try {
      setLoadingModels(true);

      const data = await getModelosByMarca(brandId);
      const safeData = Array.isArray(data) ? data : [];
      setModels(safeData);

      if (preserveModelId) {
        const foundModel =
          safeData.find((item) => item.id === preserveModelId) || null;
        setSelectedModel(foundModel);
        return foundModel;
      }

      return null;
    } catch (err) {
      setError(err.message || "No se pudieron cargar los modelos.");
      return null;
    } finally {
      setLoadingModels(false);
    }
  }

  async function reloadVersionsForModel(modelId, preserveVersionId = null) {
    try {
      setLoadingVersions(true);

      const data = await getVersionesByModelo(modelId);
      const safeData = Array.isArray(data) ? data : [];
      setVersions(safeData);

      if (preserveVersionId) {
        const foundVersion =
          safeData.find((item) => item.id === preserveVersionId) || null;
        setSelectedVersion(foundVersion);
        return foundVersion;
      }

      return null;
    } catch (err) {
      setError(err.message || "No se pudieron cargar las versiones.");
      return null;
    } finally {
      setLoadingVersions(false);
    }
  }

  async function handleSelectBrand(brand) {
    try {
      setSelectedBrand(brand);
      setSelectedModel(null);
      setSelectedVersion(null);

      setModels([]);
      setVersions([]);
      setError("");
      setSuccessMessage("");

      await reloadModelsForBrand(brand.id);
    } catch (err) {
      setError(err.message || "No se pudieron cargar los modelos.");
    }
  }

  async function handleSelectModel(model) {
    try {
      setSelectedModel(model);
      setSelectedVersion(null);

      setVersions([]);
      setError("");
      setSuccessMessage("");

      await reloadVersionsForModel(model.id);
    } catch (err) {
      setError(err.message || "No se pudieron cargar las versiones.");
    }
  }

  function handleSelectVersion(version) {
    setSelectedVersion(version);
  }

  function closeModal() {
    setModalType(null);
    setModalMode("create");
  }

  function openLoginModal() {
    setModalType("login");
  }

  function openCreateBrand() {
    setModalType("brand");
    setModalMode("create");
  }

  function openEditBrand() {
    if (!selectedBrand) return;
    setModalType("brand");
    setModalMode("edit");
  }

  function openCreateModel() {
    if (!selectedBrand) return;
    setModalType("model");
    setModalMode("create");
  }

  function openEditModel() {
    if (!selectedModel) return;
    setModalType("model");
    setModalMode("edit");
  }

  function openCreateVersion() {
    if (!selectedModel) return;
    setModalType("version");
    setModalMode("create");
  }

  function openEditVersion() {
    if (!selectedVersion) return;
    setModalType("version");
    setModalMode("edit");
  }

  async function handleLogin(formData) {
  try {
    setError("");
    setSuccessMessage("");

    await login(formData.username, formData.password);

    setLogged(true);
    setRole(getUserRole() || "USER");
    setUsername(localStorage.getItem("username") || "");

    closeModal();
    setSuccessMessage("Sesión iniciada correctamente.");
  } catch (err) {
    setError(err.message || "No se pudo iniciar sesión.");
    throw err;
  }
}

  function handleLogout() {
    logout();
    setLogged(false);
    setRole("USER");
    setUsername("");
    setSuccessMessage("Sesión cerrada correctamente.");
  }

  async function handleSubmitBrand(data) {
    try {
      setSaving(true);
      setError("");
      setSuccessMessage("");

      if (modalMode === "create") {
        const created = await createMarca(data);
        await loadBrands();

        if (created?.id) {
          setSelectedBrand(created);
          await handleSelectBrand(created);
        }

        setSuccessMessage("Marca creada correctamente.");
      } else if (selectedBrand) {
        const updated = await updateMarca(selectedBrand.id, data);
        await loadBrands();

        const updatedBrand = updated?.id
          ? updated
          : { ...selectedBrand, ...data };

        setSelectedBrand(updatedBrand);
        setSuccessMessage("Marca actualizada correctamente.");
      }

      closeModal();
    } catch (err) {
      setError(err.message || "No se pudo guardar la marca.");
    } finally {
      setSaving(false);
    }
  }

  async function handleSubmitModel(data) {
    if (!selectedBrand) return;

    try {
      setSaving(true);
      setError("");
      setSuccessMessage("");

      const payload = {
  ...data,
  marcaId: selectedBrand.id,
};

      if (modalMode === "create") {
        const created = await createModelo(payload);
        const refreshedModel = await reloadModelsForBrand(
          selectedBrand.id,
          created?.id || null
        );

        if (created?.id) {
          setSelectedModel(created);
          await reloadVersionsForModel(created.id);
        } else if (refreshedModel?.id) {
          setSelectedModel(refreshedModel);
          await reloadVersionsForModel(refreshedModel.id);
        }

        setSelectedVersion(null);
        setSuccessMessage("Modelo creado correctamente.");
      } else if (selectedModel) {
        const updated = await updateModelo(selectedModel.id, payload);
        const refreshedModel = await reloadModelsForBrand(
          selectedBrand.id,
          updated?.id || selectedModel.id
        );

        setSelectedModel(refreshedModel || { ...selectedModel, ...payload });
        setSuccessMessage("Modelo actualizado correctamente.");
      }

      closeModal();
    } catch (err) {
      setError(err.message || "No se pudo guardar el modelo.");
    } finally {
      setSaving(false);
    }
  }

  async function handleSubmitVersion(data) {
    if (!selectedModel) return;

    try {
      setSaving(true);
      setError("");
      setSuccessMessage("");

      const payload = {
  ...data,
  modeloId: selectedModel.id,
};

      if (modalMode === "create") {
        const created = await createVersion(payload);
        const refreshedVersion = await reloadVersionsForModel(
          selectedModel.id,
          created?.id || null
        );

        if (created?.id) {
          setSelectedVersion(created);
        } else if (refreshedVersion?.id) {
          setSelectedVersion(refreshedVersion);
        }

        setSuccessMessage("Versión creada correctamente.");
      } else if (selectedVersion) {
        const updated = await updateVersion(selectedVersion.id, payload);
        const refreshedVersion = await reloadVersionsForModel(
          selectedModel.id,
          updated?.id || selectedVersion.id
        );

        setSelectedVersion(
          refreshedVersion || { ...selectedVersion, ...payload }
        );
        setSuccessMessage("Versión actualizada correctamente.");
      }

      closeModal();
    } catch (err) {
      setError(err.message || "No se pudo guardar la versión.");
    } finally {
      setSaving(false);
    }
  }

  async function handleDeleteBrand() {
    if (!selectedBrand) return;

    const confirmed = window.confirm(
      `¿Seguro que deseas eliminar la marca "${getBrandName(selectedBrand)}"?`
    );
    if (!confirmed) return;

    try {
      setError("");
      setSuccessMessage("");

      await deleteMarca(selectedBrand.id);
      await loadBrands();

      setSelectedBrand(null);
      setSelectedModel(null);
      setSelectedVersion(null);
      setModels([]);
      setVersions([]);

      setSuccessMessage("Marca eliminada correctamente.");
    } catch (err) {
      setError(err.message || "No se pudo eliminar la marca.");
    }
  }

  async function handleDeleteModel() {
    if (!selectedModel || !selectedBrand) return;

    const confirmed = window.confirm(
      `¿Seguro que deseas eliminar el modelo "${getModelName(selectedModel)}"?`
    );
    if (!confirmed) return;

    try {
      setError("");
      setSuccessMessage("");

      await deleteModelo(selectedModel.id);
      await reloadModelsForBrand(selectedBrand.id);

      setSelectedModel(null);
      setSelectedVersion(null);
      setVersions([]);

      setSuccessMessage("Modelo eliminado correctamente.");
    } catch (err) {
      setError(err.message || "No se pudo eliminar el modelo.");
    }
  }

  async function handleDeleteVersion() {
    if (!selectedVersion || !selectedModel) return;

    const confirmed = window.confirm(
      `¿Seguro que deseas eliminar la versión "${getVersionName(
        selectedVersion
      )}"?`
    );
    if (!confirmed) return;

    try {
      setError("");
      setSuccessMessage("");

      await deleteVersion(selectedVersion.id);
      await reloadVersionsForModel(selectedModel.id);

      setSelectedVersion(null);
      setSuccessMessage("Versión eliminada correctamente.");
    } catch (err) {
      setError(err.message || "No se pudo eliminar la versión.");
    }
  }

  const filteredBrands = useMemo(() => {
    return filterBySearch(brands, searchTerm, (item) => item?.nombre || "");
  }, [brands, searchTerm]);

  const filteredModels = useMemo(() => {
    return filterBySearch(models, searchTerm, (item) => item?.nombre || "");
  }, [models, searchTerm]);

  const filteredVersions = useMemo(() => {
    return filterBySearch(
      versions,
      searchTerm,
      (item) => item?.nombreVersion || ""
    );
  }, [versions, searchTerm]);

  return (
    <div className="min-h-screen bg-slate-100">
      <Navbar
        role={logged ? role : "Invitado"}
        username={username}
        isAuthenticated={logged}
        onLoginClick={openLoginModal}
        onLogout={handleLogout}
      />

      <main className="mx-auto flex max-w-7xl flex-col gap-6 px-4 py-6 sm:px-6 lg:px-8">
        <SearchBar searchTerm={searchTerm} onSearchChange={setSearchTerm} />

        {logged && role === "ADMIN" && (
          <AdminPanel
            selectedBrand={selectedBrand}
            selectedModel={selectedModel}
            selectedVersion={selectedVersion}
            onOpenCreateBrand={openCreateBrand}
            onOpenEditBrand={openEditBrand}
            onDeleteBrand={handleDeleteBrand}
            onOpenCreateModel={openCreateModel}
            onOpenEditModel={openEditModel}
            onDeleteModel={handleDeleteModel}
            onOpenCreateVersion={openCreateVersion}
            onOpenEditVersion={openEditVersion}
            onDeleteVersion={handleDeleteVersion}
          />
        )}

        {successMessage && (
          <div className="rounded-2xl border border-emerald-200 bg-emerald-50 px-4 py-3 text-sm text-emerald-700">
            {successMessage}
          </div>
        )}

        {error && (
          <div className="rounded-2xl border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-700">
            {error}
          </div>
        )}

        <section className="space-y-6">
  <div className="grid gap-6 xl:grid-cols-3">
    <div>
      {loadingBrands ? (
        <Loader text="Cargando marcas..." />
      ) : (
        <BrandList
          brands={filteredBrands}
          selectedBrand={selectedBrand}
          onSelectBrand={handleSelectBrand}
        />
      )}
    </div>

    <div>
      {loadingModels ? (
        <Loader text="Cargando modelos..." />
      ) : (
        <ModelList
          models={filteredModels}
          selectedModel={selectedModel}
          onSelectModel={handleSelectModel}
          selectedBrand={selectedBrand}
        />
      )}
    </div>

    <div>
      {loadingVersions ? (
        <Loader text="Cargando versiones..." />
      ) : (
        <VersionList
          versions={filteredVersions}
          selectedVersion={selectedVersion}
          onSelectVersion={handleSelectVersion}
          selectedModel={selectedModel}
        />
      )}
    </div>
  </div>

  <div>
    {selectedBrand || selectedModel || selectedVersion ? (
      <VehicleDetail
        selectedBrand={selectedBrand}
        selectedModel={selectedModel}
        selectedVersion={selectedVersion}
        role={logged ? role : "USER"}
      />
    ) : (
      <EmptyState
        title="Explora el catálogo"
        message="Selecciona una marca, después un modelo y luego una versión para ver su información."
      />
    )}
  </div>
</section>

        <Modal
          isOpen={modalType === "login"}
          title="Iniciar sesión"
          onClose={closeModal}
        >
          <LoginForm onSubmit={handleLogin} onCancel={closeModal} />
        </Modal>

        <Modal
          isOpen={modalType === "brand"}
          title={
            modalMode === "create"
              ? "Agregar marca"
              : `Editar marca: ${selectedBrand ? getBrandName(selectedBrand) : ""}`
          }
          onClose={closeModal}
        >
          <MarcaForm
            initialData={modalMode === "edit" ? selectedBrand : null}
            onSubmit={handleSubmitBrand}
            onCancel={closeModal}
          />
          {saving && <p className="mt-4 text-sm text-slate-500">Guardando...</p>}
        </Modal>

        <Modal
          isOpen={modalType === "model"}
          title={
            modalMode === "create"
              ? "Agregar modelo"
              : `Editar modelo: ${selectedModel ? getModelName(selectedModel) : ""}`
          }
          onClose={closeModal}
        >
          <ModeloForm
            initialData={modalMode === "edit" ? selectedModel : null}
            onSubmit={handleSubmitModel}
            onCancel={closeModal}
          />
          {saving && <p className="mt-4 text-sm text-slate-500">Guardando...</p>}
        </Modal>

        <Modal
          isOpen={modalType === "version"}
          title={
            modalMode === "create"
              ? "Agregar versión"
              : `Editar versión: ${
                  selectedVersion ? getVersionName(selectedVersion) : ""
                }`
          }
          onClose={closeModal}
        >
          <VersionForm
            initialData={modalMode === "edit" ? selectedVersion : null}
            onSubmit={handleSubmitVersion}
            onCancel={closeModal}
          />
          {saving && <p className="mt-4 text-sm text-slate-500">Guardando...</p>}
        </Modal>
      </main>
    </div>
  );
}

export default CatalogPage;