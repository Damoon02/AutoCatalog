import { apiRequest } from "./api";

export function getMarcas() {
  return apiRequest("/api/marcas");
}

export function getMarcaById(id) {
  return apiRequest(`/api/marcas/${id}`);
}

export function createMarca(marcaData) {
  return apiRequest("/api/marcas", {
    method: "POST",
    body: JSON.stringify(marcaData),
  });
}

export function updateMarca(id, marcaData) {
  return apiRequest(`/api/marcas/${id}`, {
    method: "PUT",
    body: JSON.stringify(marcaData),
  });
}

export function deleteMarca(id) {
  return apiRequest(`/api/marcas/${id}`, {
    method: "DELETE",
  });
}