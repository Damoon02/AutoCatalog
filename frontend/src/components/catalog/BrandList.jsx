import { getBrandName } from "../../utils/catalogUtils";
import EmptyState from "../ui/EmptyState";

function BrandList({ brands, selectedBrand, onSelectBrand }) {
  return (
    <section className="rounded-2xl border border-slate-200 bg-white p-4 shadow-sm">
      <h2 className="mb-4 text-lg font-semibold text-slate-900">Marcas</h2>

      {brands.length === 0 ? (
        <EmptyState
          title="Sin marcas"
          message="No hay marcas disponibles para mostrar."
        />
      ) : (
        <div className="space-y-2">
          {brands.map((brand) => {
            const isSelected = selectedBrand?.id === brand.id;

            return (
              <button
                key={brand.id}
                onClick={() => onSelectBrand(brand)}
                className={`w-full rounded-xl px-4 py-3 text-left text-sm font-medium transition ${
                  isSelected
                    ? "bg-blue-600 text-white shadow"
                    : "bg-slate-50 text-slate-700 hover:bg-slate-100"
                }`}
              >
                {getBrandName(brand)}
              </button>
            );
          })}
        </div>
      )}
    </section>
  );
}

export default BrandList;