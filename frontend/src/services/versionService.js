import { apiRequest } from "./api";

export function getVersiones() {
  return apiRequest("/api/versiones");
}

export function getVersionById(id) {
  return apiRequest(`/api/versiones/${id}`);
}

export function getVersionesByModelo(modeloId) {
  return apiRequest(`/api/versiones/modelo/${modeloId}`);
}

export function createVersion(versionData) {
  return apiRequest("/api/versiones", {
    method: "POST",
    body: JSON.stringify(versionData),
  });
}

export function updateVersion(id, versionData) {
  return apiRequest(`/api/versiones/${id}`, {
    method: "PUT",
    body: JSON.stringify(versionData),
  });
}

export function deleteVersion(id) {
  return apiRequest(`/api/versiones/${id}`, {
    method: "DELETE",
  });
}