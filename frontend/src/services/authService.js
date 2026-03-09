import { apiRequest, setToken, removeToken, getToken } from "./api";

export async function login(username, password) {
  const data = await apiRequest("/auth/login", {
    method: "POST",
    body: JSON.stringify({ username, password }),
  });

  if (data.token) {
    setToken(data.token);
  }

  return data;
}

export function logout() {
  removeToken();
}

export function isAuthenticated() {
  return !!getToken();
}