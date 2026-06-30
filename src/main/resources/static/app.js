const API = "/api";

const Auth = {
  save(data) {
    localStorage.setItem("f1_token",     data.token);
    localStorage.setItem("f1_firstName", data.firstName);
    localStorage.setItem("f1_lastName",  data.lastName);
    localStorage.setItem("f1_email",     data.email);
  },
  clear() {
    ["f1_token","f1_firstName","f1_lastName","f1_email"].forEach(k => localStorage.removeItem(k));
  },
  token()     { return localStorage.getItem("f1_token"); },
  firstName() { return localStorage.getItem("f1_firstName"); },
  isLoggedIn(){ return !!localStorage.getItem("f1_token"); },
};

async function apiFetch(path, options = {}) {
  const headers = { "Content-Type": "application/json", ...(options.headers || {}) };
  if (Auth.token()) headers["Authorization"] = "Bearer " + Auth.token();
  const res = await fetch(API + path, { ...options, headers });
  if (res.status === 401) {
    Auth.clear();
    window.location.href = "/login.html?next=" + encodeURIComponent(window.location.pathname);
    throw new Error("Unauthorized");
  }
  const text = await res.text();
  const data = text ? JSON.parse(text) : null;
  if (!res.ok) {
    const msg = data?.message || data?.details?.join(", ") || "შეცდომა";
    throw new Error(msg);
  }
  return data;
}

function showToast(msg, duration = 3000) {
  let el = document.getElementById("toast");
  if (!el) { el = document.createElement("div"); el.id = "toast"; document.body.appendChild(el); }
  el.textContent = msg;
  el.classList.add("show");
  clearTimeout(el._t);
  el._t = setTimeout(() => el.classList.remove("show"), duration);
}

function renderNav(activePage) {
  const nav = document.getElementById("main-nav");
  if (!nav) return;
  const loggedIn = Auth.isLoggedIn();
  nav.innerHTML = `
    <div class="nav-inner">
      <a href="/index.html" class="nav-logo">F1<span>STATS</span></a>
      <div class="nav-links">
        <a href="/index.html"   class="${activePage === "schedule" ? "active" : ""}">Schedule</a>
        <a href="/drivers.html" class="${activePage === "drivers"  ? "active" : ""}">Drivers</a>
      </div>
      <div class="nav-auth">
        ${loggedIn
          ? `<span class="nav-user">${Auth.firstName()}</span>
             <button class="btn btn-ghost btn-sm" onclick="logout()">გამოსვლა</button>`
          : `<a href="/login.html"    class="btn btn-ghost btn-sm">შესვლა</a>
             <a href="/register.html" class="btn btn-primary btn-sm">რეგისტრაცია</a>`
        }
      </div>
    </div>`;
}

function logout() { Auth.clear(); window.location.href = "/index.html"; }

function guardAuth() {
  if (!Auth.isLoggedIn()) {
    window.location.href = "/login.html?next=" + encodeURIComponent(window.location.pathname);
  }
}

function fmtDate(dateStr) {
  if (!dateStr) return "—";
  const d = new Date(dateStr);
  return d.toLocaleDateString("ka-GE", { day: "numeric", month: "short", year: "numeric" });
}

function fmtDateTime(isoStr) {
  if (!isoStr) return "—";
  const d = new Date(isoStr);
  return d.toLocaleString("ka-GE", { day: "numeric", month: "short", hour: "2-digit", minute: "2-digit", timeZoneName: "short" });
}

function statusBadge(status) {
  const map = {
    COMPLETED: "<span class=\"badge badge-completed\">დასრულდა</span>",
    UPCOMING:  "<span class=\"badge badge-upcoming\">მოლოდინში</span>",
    LIVE:      "<span class=\"badge badge-live\">● LIVE</span>",
  };
  return map[status] || "";
}

function posClass(pos) {
  if (pos === 1) return "p1";
  if (pos === 2) return "p2";
  if (pos === 3) return "p3";
  return "";
}
