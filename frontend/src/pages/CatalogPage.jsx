import { useEffect, useMemo, useState } from "react";
import Navbar from "../components/layout/Navbar";
import SearchBar from "../components/catalog/SearchBar";
import BrandList from "../components/catalog/BrandList";
import ModelList from "../components/catalog/ModelList";
import VersionList from "../components/catalog/VersionList";
import VehicleDetail from "../components/catalog/VehicleDetail";
import Loader from "../components/ui/Loader";
import EmptyState from "../components/ui/EmptyState";

import { getMarcas } from "../services/marcaService";
import { getModelosByMarca } from "../services/modeloService";
import { getVersionesByModelo } from "../services/versionService";
import { filterBySearch } from "../utils/catalogUtils";
import { isAuthenticated, logout } from "../services/authService";

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
  const [error, setError] = useState("");

  const [role, setRole] = useState(localStorage.getItem("role") || "USER");
  const [logged, setLogged] = useState(isAuthenticated());

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

  async function handleSelectBrand(brand) {
    try {
      setSelectedBrand(brand);
      setSelectedModel(null);
      setSelectedVersion(null);
      setModels([]);
      setVersions([]);
      setLoadingModels(true);
      setError("");

      const data = await getModelosByMarca(brand.id);
      setModels(Array.isArray(data) ? data : []);
    } catch (err) {
      setError(err.message || "No se pudieron cargar los modelos.");
    } finally {
      setLoadingModels(false);
    }
  }

  async function handleSelectModel(model) {
    try {
      setSelectedModel(model);
      setSelectedVersion(null);
      setVersions([]);
      setLoadingVersions(true);
      setError("");

      const data = await getVersionesByModelo(model.id);
      setVersions(Array.isArray(data) ? data : []);
    } catch (err) {
      setError(err.message || "No se pudieron cargar las versiones.");
    } finally {
      setLoadingVersions(false);
    }
  }

  function handleSelectVersion(version) {
    setSelectedVersion(version);
  }

  function handleLogout() {
    logout();
    localStorage.removeItem("role");
    setLogged(false);
    setRole("USER");
  }

  function handleMockLogin() {
    localStorage.setItem("role", "ADMIN");
    setRole("ADMIN");
    setLogged(true);
  }

  const filteredBrands = useMemo(
    () => filterBySearch(brands, searchTerm),
    [brands, searchTerm]
  );

  const filteredModels = useMemo(
    () => filterBySearch(models, searchTerm),
    [models, searchTerm]
  );

  const filteredVersions = useMemo(
    () => filterBySearch(versions, searchTerm),
    [versions, searchTerm]
  );

  return (
    <div className="min-h-screen bg-slate-100">
      <Navbar
        role={role}
        isAuthenticated={logged}
        onLogout={handleLogout}
        onMockLogin={handleMockLogin}
      />

      <main className="mx-auto flex max-w-7xl flex-col gap-6 px-4 py-6 sm:px-6 lg:px-8">
        <SearchBar searchTerm={searchTerm} onSearchChange={setSearchTerm} />

        {error && (
          <div className="rounded-2xl border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-700">
            {error}
          </div>
        )}

        <section className="grid gap-6 xl:grid-cols-[260px_260px_260px_minmax(0,1fr)]">
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

          <div>
            {selectedBrand || selectedModel || selectedVersion ? (
              <VehicleDetail
                selectedBrand={selectedBrand}
                selectedModel={selectedModel}
                selectedVersion={selectedVersion}
                role={role}
              />
            ) : (
              <EmptyState
                title="Explora el catálogo"
                message="Selecciona una marca, después un modelo y luego una versión para ver su información."
              />
            )}
          </div>
        </section>
      </main>
    </div>
  );
}

export default CatalogPage;