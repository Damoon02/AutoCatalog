import { useEffect, useState } from "react";

function MarcaForm({ initialData = null, onSubmit, onCancel }) {
  const [form, setForm] = useState({
    nombre: "",
    paisOrigen: "",
  });

  useEffect(() => {
    if (initialData) {
      setForm({
        nombre: initialData.nombre || "",
        paisOrigen: initialData.paisOrigen || "",
      });
    }
  }, [initialData]);

  function handleChange(e) {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  }

  function handleSubmit(e) {
    e.preventDefault();
    onSubmit(form);
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <label className="mb-1 block text-sm font-medium text-slate-700">
          Nombre
        </label>
        <input
          type="text"
          name="nombre"
          value={form.nombre}
          onChange={handleChange}
          className="w-full rounded-xl border border-slate-300 px-4 py-3"
          required
        />
      </div>

      <div>
        <label className="mb-1 block text-sm font-medium text-slate-700">
          País de origen
        </label>
        <input
          type="text"
          name="paisOrigen"
          value={form.paisOrigen}
          onChange={handleChange}
          className="w-full rounded-xl border border-slate-300 px-4 py-3"
          required
        />
      </div>

      <div className="flex justify-end gap-3">
        <button
          type="button"
          onClick={onCancel}
          className="rounded-xl border border-slate-300 px-4 py-2 text-sm font-medium text-slate-700"
        >
          Cancelar
        </button>
        <button
          type="submit"
          className="rounded-xl bg-slate-900 px-4 py-2 text-sm font-medium text-white"
        >
          Guardar
        </button>
      </div>
    </form>
  );
}

export default MarcaForm;