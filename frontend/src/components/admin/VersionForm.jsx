import { useEffect, useState } from "react";

function VersionForm({ initialData = null, onSubmit, onCancel }) {
  const [form, setForm] = useState({
    nombreVersion: "",
    traccion: "",
    caballosFuerza: "",
    capacidadCarga: "",
    precioReferencia: "",
    descripcionBreve: "",
  });

  useEffect(() => {
    if (initialData) {
      setForm({
        nombreVersion: initialData.nombreVersion || "",
        traccion: initialData.traccion || "",
        caballosFuerza: initialData.caballosFuerza || "",
        capacidadCarga: initialData.capacidadCarga || "",
        precioReferencia: initialData.precioReferencia || "",
        descripcionBreve: initialData.descripcionBreve || "",
      });
    }
  }, [initialData]);

  function handleChange(e) {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  }

  function handleSubmit(e) {
    e.preventDefault();

    onSubmit({
      ...form,
      caballosFuerza: Number(form.caballosFuerza),
      capacidadCarga: Number(form.capacidadCarga),
      precioReferencia: Number(form.precioReferencia),
    });
  }

  return (
    <form onSubmit={handleSubmit} className="grid gap-4 md:grid-cols-2">
      <div>
        <label className="mb-1 block text-sm font-medium text-slate-700">Nombre de versión</label>
        <input
          type="text"
          name="nombreVersion"
          value={form.nombreVersion}
          onChange={handleChange}
          className="w-full rounded-xl border border-slate-300 px-4 py-3"
          required
        />
      </div>

      <div>
        <label className="mb-1 block text-sm font-medium text-slate-700">Tracción</label>
        <input
          type="text"
          name="traccion"
          value={form.traccion}
          onChange={handleChange}
          className="w-full rounded-xl border border-slate-300 px-4 py-3"
          required
        />
      </div>

      <div>
        <label className="mb-1 block text-sm font-medium text-slate-700">Caballos de fuerza</label>
        <input
          type="number"
          name="caballosFuerza"
          value={form.caballosFuerza}
          onChange={handleChange}
          className="w-full rounded-xl border border-slate-300 px-4 py-3"
          required
        />
      </div>

      <div>
        <label className="mb-1 block text-sm font-medium text-slate-700">Capacidad de carga</label>
        <input
          type="number"
          step="0.01"
          name="capacidadCarga"
          value={form.capacidadCarga}
          onChange={handleChange}
          className="w-full rounded-xl border border-slate-300 px-4 py-3"
          required
        />
      </div>

      <div>
        <label className="mb-1 block text-sm font-medium text-slate-700">Precio de referencia</label>
        <input
          type="number"
          step="0.01"
          name="precioReferencia"
          value={form.precioReferencia}
          onChange={handleChange}
          className="w-full rounded-xl border border-slate-300 px-4 py-3"
          required
        />
      </div>

      <div className="md:col-span-2">
        <label className="mb-1 block text-sm font-medium text-slate-700">Descripción breve</label>
        <textarea
          name="descripcionBreve"
          value={form.descripcionBreve}
          onChange={handleChange}
          rows="4"
          className="w-full rounded-xl border border-slate-300 px-4 py-3"
          required
        />
      </div>

      <div className="md:col-span-2 flex justify-end gap-3">
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

export default VersionForm;