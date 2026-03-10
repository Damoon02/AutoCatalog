export function getBrandName(brand) {
  return brand?.nombre || "Marca sin nombre";
}

export function getCountryName(brand) {
  return brand?.paisOrigen || "No disponible";
}

export function getModelName(model) {
  return model?.nombre || "Modelo sin nombre";
}

export function getModelYear(model) {
  return model?.anio ?? "No disponible";
}

export function getModelEngine(model) {
  return model?.motor || "No disponible";
}

export function getModelFuel(model) {
  return model?.combustible || "No disponible";
}

export function getModelTransmission(model) {
  return model?.transmision || "No disponible";
}

export function getModelDescription(model) {
  return model?.descripcionBreve || "Sin descripción disponible.";
}

export function getModelImage(model) {
  return (
    model?.imagenUrl ||
    "https://placehold.co/800x500?text=AutoCatalog"
  );
}

export function getVersionName(version) {
  return version?.nombreVersion || "Versión sin nombre";
}

export function getVersionTraction(version) {
  return version?.traccion || "No disponible";
}

export function getVersionHorsepower(version) {
  return version?.caballosFuerza ?? "No disponible";
}

export function getVersionLoadCapacity(version) {
  return version?.capacidadCarga ?? "No disponible";
}

export function getVersionPrice(version) {
  return version?.precioReferencia ?? "No disponible";
}

export function getVersionDescription(version) {
  return version?.descripcionBreve || "Sin descripción disponible.";
}

export function filterBySearch(items, searchTerm, extractor = (item) => item?.nombre || "") {
  if (!searchTerm.trim()) return items;

  const term = searchTerm.toLowerCase();

  return items.filter((item) =>
    extractor(item).toLowerCase().includes(term)
  );
}