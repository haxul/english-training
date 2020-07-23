import typeChecker from "./typeChecker";
export const baseUrl = "http://localhost:8080"
export function createAuthHeaders(token) {
    typeChecker.requireString(token)
    return {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
    }
}

export function checkAuth() {
    const token = localStorage.getItem("token")
    if (token) return token
    else document.location.reload()
}