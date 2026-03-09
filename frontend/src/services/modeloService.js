import { apiRequest } from "./api";

export function getModelos() {
  return apiRequest("/api/modelos");
}

export function getModeloById(id) {
  return apiRequest(`/api/modelos/${id}`);
}

export function getModelosByMarca(marcaId) {
  return apiRequest(`/api/modelos/marca/${marcaId}`);
}

export function createModelo(modeloData) {
  return apiRequest("/api/modelos", {
    method: "POST",
    body: JSON.stringify(modeloData),
  });
}

export function updateModelo(id, modeloData) {
  return apiRequest(`/api/modelos/${id}`, {
    method: "PUT",
    body: JSON.stringify(modeloData),
  });
}

export function deleteModelo(id) {
  return apiRequest(`/api/modelos/${id}`, {
    method: "DELETE",
  });
}