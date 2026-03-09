import { getVersionName } from "../../utils/catalogUtils";
import EmptyState from "../ui/EmptyState";

function VersionList({
  versions,
  selectedVersion,
  onSelectVersion,
  selectedModel,
}) {
  return (
    <section className="rounded-2xl border border-slate-200 bg-white p-4 shadow-sm">
      <h2 className="mb-4 text-lg font-semibold text-slate-900">Versiones</h2>

      {!selectedModel ? (
        <EmptyState
          title="Selecciona un modelo"
          message="Primero elige un modelo para ver sus versiones."
        />
      ) : versions.length === 0 ? (
        <EmptyState
          title="Sin versiones"
          message="No hay versiones disponibles para este modelo."
        />
      ) : (
        <div className="space-y-2">
          {versions.map((version) => {
            const isSelected = selectedVersion?.id === version.id;

            return (
              <button
                key={version.id}
                onClick={() => onSelectVersion(version)}
                className={`w-full rounded-xl px-4 py-3 text-left text-sm font-medium transition ${
                  isSelected
                    ? "bg-blue-600 text-white shadow"
                    : "bg-slate-50 text-slate-700 hover:bg-slate-100"
                }`}
              >
                {getVersionName(version)}
              </button>
            );
          })}
        </div>
      )}
    </section>
  );
}

export default VersionList;