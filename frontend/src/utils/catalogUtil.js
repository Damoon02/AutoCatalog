export function getItemName(item) {
  return (
    item?.nombre ||
    item?.name ||
    item?.marca ||
    item?.modelo ||
    item?.version ||
    "Sin nombre"
  );
}

export function getBrandName(brand) {
  return brand?.nombre || brand?.name || brand?.marca || "Marca sin nombre";
}

export function getModelName(model) {
  return model?.nombre || model?.name || model?.modelo || "Modelo sin nombre";
}

export function getVersionName(version) {
  return version?.nombre || version?.name || version?.version || "Versión sin nombre";
}

export function getVehicleImage(version) {
  return (
    version?.imagen ||
    version?.image ||
    version?.imageUrl ||
    "https://placehold.co/800x500?text=AutoCatalog"
  );
}

export function getVehicleYear(version) {
  return version?.anio || version?.año || version?.year || "No disponible";
}

export function getVehicleEngine(version) {
  return version?.motor || "No disponible";
}

export function getVehicleFuel(version) {
  return version?.combustible || version?.fuel || "No disponible";
}

export function getVehicleTransmission(version) {
  return version?.transmision || version?.transmisión || version?.transmission || "No disponible";
}

export function getVehicleDescription(version) {
  return (
    version?.descripcion ||
    version?.description ||
    "Sin descripción disponible."
  );
}

export function filterBySearch(items, searchTerm) {
  if (!searchTerm.trim()) return items;

  const term = searchTerm.toLowerCase();

  return items.filter((item) =>
    getItemName(item).toLowerCase().includes(term)
  );
}