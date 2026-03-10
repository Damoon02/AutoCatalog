function Navbar({
  role = "Invitado",
  username = "",
  isAuthenticated = false,
  onLoginClick,
  onLogout,
}) {
  return (
    <header className="border-b border-slate-200 bg-white/90 backdrop-blur">
      <div className="mx-auto flex max-w-7xl items-center justify-between px-4 py-4 sm:px-6 lg:px-8">
        <div>
          <h1 className="text-xl font-bold tracking-tight text-slate-900">
            AutoCatalog
          </h1>
          <p className="text-sm text-slate-500">
            Catálogo dinámico de vehículos
          </p>
        </div>

        <div className="flex items-center gap-3">
          {isAuthenticated && (
            <span className="hidden rounded-full bg-slate-100 px-3 py-1 text-sm font-medium text-slate-700 sm:inline-flex">
              Usuario: {username || "Sin usuario"}
            </span>
          )}

          <span
            className={`rounded-full px-3 py-1 text-sm font-medium ${
              role === "ADMIN"
                ? "bg-amber-100 text-amber-800"
                : "bg-slate-100 text-slate-700"
            }`}
          >
            Rol: {role}
          </span>

          {isAuthenticated ? (
            <button
              onClick={onLogout}
              className="rounded-lg bg-slate-900 px-4 py-2 text-sm font-medium text-white transition hover:bg-slate-700"
            >
              Logout
            </button>
          ) : (
            <button
              onClick={onLoginClick}
              className="rounded-lg bg-blue-600 px-4 py-2 text-sm font-medium text-white transition hover:bg-blue-500"
            >
              Login
            </button>
          )}
        </div>
      </div>
    </header>
  );
}

export default Navbar;