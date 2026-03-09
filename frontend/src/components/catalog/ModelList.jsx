import { getModelName } from "../../utils/catalogUtils";
import EmptyState from "../ui/EmptyState";

function ModelList({ models, selectedModel, onSelectModel, selectedBrand }) {
  return (
    <section className="rounded-2xl border border-slate-200 bg-white p-4 shadow-sm">
      <h2 className="mb-4 text-lg font-semibold text-slate-900">Modelos</h2>

      {!selectedBrand ? (
        <EmptyState
          title="Selecciona una marca"
          message="Primero elige una marca para ver sus modelos."
        />
      ) : models.length === 0 ? (
        <EmptyState
          title="Sin modelos"
          message="No hay modelos disponibles para esta marca."
        />
      ) : (
        <div className="space-y-2">
          {models.map((model) => {
            const isSelected = selectedModel?.id === model.id;

            return (
              <button
                key={model.id}
                onClick={() => onSelectModel(model)}
                className={`w-full rounded-xl px-4 py-3 text-left text-sm font-medium transition ${
                  isSelected
                    ? "bg-blue-600 text-white shadow"
                    : "bg-slate-50 text-slate-700 hover:bg-slate-100"
                }`}
              >
                {getModelName(model)}
              </button>
            );
          })}
        </div>
      )}
    </section>
  );
}

export default ModelList;