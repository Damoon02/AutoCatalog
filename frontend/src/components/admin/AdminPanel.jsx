function AdminPanel({
  selectedBrand,
  selectedModel,
  selectedVersion,
  onOpenCreateBrand,
  onOpenEditBrand,
  onDeleteBrand,
  onOpenCreateModel,
  onOpenEditModel,
  onDeleteModel,
  onOpenCreateVersion,
  onOpenEditVersion,
  onDeleteVersion,
}) {
  return (
    <section className="rounded-2xl border border-amber-200 bg-amber-50 p-4 shadow-sm">
      <div className="mb-4">
        <h2 className="text-lg font-semibold text-amber-900">
          Panel de administración
        </h2>
        <p className="text-sm text-amber-700">
          Desde aquí podrás agregar, editar o eliminar datos del catálogo.
        </p>
      </div>

      <div className="grid gap-3 md:grid-cols-3">
        <div className="rounded-xl bg-white p-4 shadow-sm">
          <h3 className="mb-3 font-semibold text-slate-900">Marca</h3>
          <div className="flex flex-col gap-2">
            <button
              onClick={onOpenCreateBrand}
              className="rounded-lg bg-slate-900 px-4 py-2 text-sm font-medium text-white hover:bg-slate-700"
            >
              Agregar marca
            </button>
            <button
              onClick={onOpenEditBrand}
              disabled={!selectedBrand}
              className="rounded-lg bg-blue-600 px-4 py-2 text-sm font-medium text-white hover:bg-blue-500 disabled:cursor-not-allowed disabled:bg-slate-300"
            >
              Editar marca
            </button>
            <button
              onClick={onDeleteBrand}
              disabled={!selectedBrand}
              className="rounded-lg bg-red-600 px-4 py-2 text-sm font-medium text-white hover:bg-red-500 disabled:cursor-not-allowed disabled:bg-slate-300"
            >
              Eliminar marca
            </button>
          </div>
        </div>

        <div className="rounded-xl bg-white p-4 shadow-sm">
          <h3 className="mb-3 font-semibold text-slate-900">Modelo</h3>
          <div className="flex flex-col gap-2">
            <button
              onClick={onOpenCreateModel}
              disabled={!selectedBrand}
              className="rounded-lg bg-slate-900 px-4 py-2 text-sm font-medium text-white hover:bg-slate-700 disabled:cursor-not-allowed disabled:bg-slate-300"
            >
              Agregar modelo
            </button>
            <button
              onClick={onOpenEditModel}
              disabled={!selectedModel}
              className="rounded-lg bg-blue-600 px-4 py-2 text-sm font-medium text-white hover:bg-blue-500 disabled:cursor-not-allowed disabled:bg-slate-300"
            >
              Editar modelo
            </button>
            <button
              onClick={onDeleteModel}
              disabled={!selectedModel}
              className="rounded-lg bg-red-600 px-4 py-2 text-sm font-medium text-white hover:bg-red-500 disabled:cursor-not-allowed disabled:bg-slate-300"
            >
              Eliminar modelo
            </button>
          </div>
        </div>

        <div className="rounded-xl bg-white p-4 shadow-sm">
          <h3 className="mb-3 font-semibold text-slate-900">Versión</h3>
          <div className="flex flex-col gap-2">
            <button
              onClick={onOpenCreateVersion}
              disabled={!selectedModel}
              className="rounded-lg bg-slate-900 px-4 py-2 text-sm font-medium text-white hover:bg-slate-700 disabled:cursor-not-allowed disabled:bg-slate-300"
            >
              Agregar versión
            </button>
            <button
              onClick={onOpenEditVersion}
              disabled={!selectedVersion}
              className="rounded-lg bg-blue-600 px-4 py-2 text-sm font-medium text-white hover:bg-blue-500 disabled:cursor-not-allowed disabled:bg-slate-300"
            >
              Editar versión
            </button>
            <button
              onClick={onDeleteVersion}
              disabled={!selectedVersion}
              className="rounded-lg bg-red-600 px-4 py-2 text-sm font-medium text-white hover:bg-red-500 disabled:cursor-not-allowed disabled:bg-slate-300"
            >
              Eliminar versión
            </button>
          </div>
        </div>
      </div>
    </section>
  );
}

export default AdminPanel;