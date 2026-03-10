import { useEffect, useState } from "react";

function ModeloForm({ initialData = null, onSubmit, onCancel }) {
  const [form, setForm] = useState({
    nombre: "",
    anio: "",
    motor: "",
    combustible: "",
    transmision: "",
    descripcionBreve: "",
    imagenUrl: "",
  });

  useEffect(() => {
    if (initialData) {
      setForm({
        nombre: initialData.nombre || "",
        anio: initialData.anio || "",
        motor: initialData.motor || "",
        combustible: initialData.combustible || "",
        transmision: initialData.transmision || "",
        descripcionBreve: initialData.descripcionBreve || "",
        imagenUrl: initialData.imagenUrl || "",
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
      anio: Number(form.anio),
    });
  }

  return (
    <form onSubmit={handleSubmit} className="grid gap-4 md:grid-cols-2">
      <div>
        <label className="mb-1 block text-sm font-medium text-slate-700">Nombre</label>
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
        <label className="mb-1 block text-sm font-medium text-slate-700">Año</label>
        <input
          type="number"
          name="anio"
          value={form.anio}
          onChange={handleChange}
          className="w-full rounded-xl border border-slate-300 px-4 py-3"
          required
        />
      </div>

      <div>
        <label className="mb-1 block text-sm font-medium text-slate-700">Motor</label>
        <input
          type="text"
          name="motor"
          value={form.motor}
          onChange={handleChange}
          className="w-full rounded-xl border border-slate-300 px-4 py-3"
          required
        />
      </div>

      <div>
        <label className="mb-1 block text-sm font-medium text-slate-700">Combustible</label>
        <input
          type="text"
          name="combustible"
          value={form.combustible}
          onChange={handleChange}
          className="w-full rounded-xl border border-slate-300 px-4 py-3"
          required
        />
      </div>

      <div>
        <label className="mb-1 block text-sm font-medium text-slate-700">Transmisión</label>
        <input
          type="text"
          name="transmision"
          value={form.transmision}
          onChange={handleChange}
          className="w-full rounded-xl border border-slate-300 px-4 py-3"
          required
        />
      </div>

      <div>
        <label className="mb-1 block text-sm font-medium text-slate-700">Imagen URL</label>
        <input
          type="text"
          name="imagenUrl"
          value={form.imagenUrl}
          onChange={handleChange}
          className="w-full rounded-xl border border-slate-300 px-4 py-3"
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

export default ModeloForm;