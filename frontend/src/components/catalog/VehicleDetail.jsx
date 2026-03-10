import {
  getBrandName,
  getCountryName,
  getModelName,
  getModelYear,
  getModelEngine,
  getModelFuel,
  getModelTransmission,
  getModelDescription,
  getModelImage,
  getVersionName,
  getVersionTraction,
  getVersionHorsepower,
  getVersionLoadCapacity,
  getVersionPrice,
  getVersionDescription,
} from "../../utils/catalogUtils";
import EmptyState from "../ui/EmptyState";

function VehicleDetail({ selectedBrand, selectedModel, selectedVersion, role }) {
  if (!selectedModel) {
    return (
      <EmptyState
        title="Selecciona un modelo"
        message="Aquí se mostrará la información del vehículo y su versión."
      />
    );
  }

  return (
    <section className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
      <div className="grid gap-6 lg:grid-cols-2">
        <div className="overflow-hidden rounded-2xl border border-slate-200 bg-slate-50">
          <img
            src={getModelImage(selectedModel)}
            alt={getModelName(selectedModel)}
            className="h-full min-h-[280px] w-full object-cover"
          />
        </div>

        <div className="flex flex-col justify-between">
          <div>
            <div className="mb-3 flex items-center gap-2">
              <span className="rounded-full bg-blue-100 px-3 py-1 text-xs font-semibold text-blue-700">
                {role === "ADMIN" ? "Modo administrador" : "Modo usuario"}
              </span>

              {selectedBrand && (
                <span className="rounded-full bg-slate-100 px-3 py-1 text-xs font-semibold text-slate-700">
                  {getCountryName(selectedBrand)}
                </span>
              )}
            </div>

            <h2 className="text-2xl font-bold text-slate-900">
              {selectedBrand ? `${getBrandName(selectedBrand)} ` : ""}
              {getModelName(selectedModel)}
              {selectedVersion ? ` ${getVersionName(selectedVersion)}` : ""}
            </h2>

            <p className="mt-3 text-sm leading-6 text-slate-600">
              {selectedVersion
                ? getVersionDescription(selectedVersion)
                : getModelDescription(selectedModel)}
            </p>

            <div className="mt-6 grid grid-cols-1 gap-3 sm:grid-cols-2">
              <div className="rounded-xl bg-slate-50 p-3">
                <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">
                  Año
                </p>
                <p className="mt-1 text-sm font-medium text-slate-800">
                  {getModelYear(selectedModel)}
                </p>
              </div>

              <div className="rounded-xl bg-slate-50 p-3">
                <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">
                  Motor
                </p>
                <p className="mt-1 text-sm font-medium text-slate-800">
                  {getModelEngine(selectedModel)}
                </p>
              </div>

              <div className="rounded-xl bg-slate-50 p-3">
                <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">
                  Combustible
                </p>
                <p className="mt-1 text-sm font-medium text-slate-800">
                  {getModelFuel(selectedModel)}
                </p>
              </div>

              <div className="rounded-xl bg-slate-50 p-3">
                <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">
                  Transmisión
                </p>
                <p className="mt-1 text-sm font-medium text-slate-800">
                  {getModelTransmission(selectedModel)}
                </p>
              </div>

              <div className="rounded-xl bg-slate-50 p-3">
                <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">
                  Tracción
                </p>
                <p className="mt-1 text-sm font-medium text-slate-800">
                  {selectedVersion
                    ? getVersionTraction(selectedVersion)
                    : "Selecciona una versión"}
                </p>
              </div>

              <div className="rounded-xl bg-slate-50 p-3">
                <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">
                  Caballos de fuerza
                </p>
                <p className="mt-1 text-sm font-medium text-slate-800">
                  {selectedVersion
                    ? getVersionHorsepower(selectedVersion)
                    : "Selecciona una versión"}
                </p>
              </div>

              <div className="rounded-xl bg-slate-50 p-3">
                <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">
                  Capacidad de carga
                </p>
                <p className="mt-1 text-sm font-medium text-slate-800">
                  {selectedVersion
                    ? `${getVersionLoadCapacity(selectedVersion)} kg`
                    : "Selecciona una versión"}
                </p>
              </div>

              <div className="rounded-xl bg-slate-50 p-3">
                <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">
                  Precio de referencia
                </p>
                <p className="mt-1 text-sm font-medium text-slate-800">
                  {selectedVersion
                    ? `$${getVersionPrice(selectedVersion)}`
                    : "Selecciona una versión"}
                </p>
              </div>
            </div>
          </div>

          <div className="mt-6 rounded-xl border border-slate-200 bg-slate-50 p-4">
            <p className="text-sm text-slate-500">
              Toda la navegación ocurre dentro de la misma vista.
            </p>
          </div>
        </div>
      </div>
    </section>
  );
}

export default VehicleDetail;