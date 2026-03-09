import {
  getBrandName,
  getModelName,
  getVersionName,
  getVehicleDescription,
  getVehicleEngine,
  getVehicleFuel,
  getVehicleImage,
  getVehicleTransmission,
  getVehicleYear,
} from "../../utils/catalogUtils";
import EmptyState from "../ui/EmptyState";

function VehicleDetail({ selectedBrand, selectedModel, selectedVersion, role }) {
  if (!selectedVersion) {
    return (
      <EmptyState
        title="Selecciona una versión"
        message="Aquí se mostrará la información del vehículo seleccionado."
      />
    );
  }

  return (
    <section className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
      <div className="grid gap-6 lg:grid-cols-2">
        <div className="overflow-hidden rounded-2xl border border-slate-200 bg-slate-50">
          <img
            src={getVehicleImage(selectedVersion)}
            alt={getVersionName(selectedVersion)}
            className="h-full min-h-[280px] w-full object-cover"
          />
        </div>

        <div className="flex flex-col justify-between">
          <div>
            <div className="mb-3 flex items-center gap-2">
              <span className="rounded-full bg-blue-100 px-3 py-1 text-xs font-semibold text-blue-700">
                {role === "ADMIN" ? "Modo administrador" : "Modo usuario"}
              </span>
            </div>

            <h2 className="text-2xl font-bold text-slate-900">
              {getBrandName(selectedBrand)} {getModelName(selectedModel)}{" "}
              {getVersionName(selectedVersion)}
            </h2>

            <p className="mt-3 text-sm leading-6 text-slate-600">
              {getVehicleDescription(selectedVersion)}
            </p>

            <div className="mt-6 grid grid-cols-1 gap-3 sm:grid-cols-2">
              <div className="rounded-xl bg-slate-50 p-3">
                <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">
                  Año
                </p>
                <p className="mt-1 text-sm font-medium text-slate-800">
                  {getVehicleYear(selectedVersion)}
                </p>
              </div>

              <div className="rounded-xl bg-slate-50 p-3">
                <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">
                  Motor
                </p>
                <p className="mt-1 text-sm font-medium text-slate-800">
                  {getVehicleEngine(selectedVersion)}
                </p>
              </div>

              <div className="rounded-xl bg-slate-50 p-3">
                <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">
                  Combustible
                </p>
                <p className="mt-1 text-sm font-medium text-slate-800">
                  {getVehicleFuel(selectedVersion)}
                </p>
              </div>

              <div className="rounded-xl bg-slate-50 p-3">
                <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">
                  Transmisión
                </p>
                <p className="mt-1 text-sm font-medium text-slate-800">
                  {getVehicleTransmission(selectedVersion)}
                </p>
              </div>
            </div>
          </div>

          <div className="mt-6 rounded-xl border border-slate-200 bg-slate-50 p-4">
            <p className="text-sm text-slate-500">
              Navegación dinámica dentro de una sola vista.
            </p>
          </div>
        </div>
      </div>
    </section>
  );
}

export default VehicleDetail;