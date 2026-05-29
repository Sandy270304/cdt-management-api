const loginForm = document.getElementById("loginForm");
const loginMessage = document.getElementById("loginMessage");

loginForm.addEventListener("submit", async function (event) {
    event.preventDefault();

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();

    loginMessage.textContent = "";
    loginMessage.className = "message";

    if (!username || !password) {
        showLoginError("Please enter username and password.");
        return;
    }

    try {
        const advisor = await apiGet(`/advisors/username/${encodeURIComponent(username)}`);

        if (!advisor.active) {
            showLoginError("Advisor account is inactive.");
            return;
        }

        if (advisor.passwordHash !== password) {
            showLoginError("Invalid username or password.");
            return;
        }

        localStorage.setItem("advisorId", advisor.id);
        localStorage.setItem("advisorUsername", advisor.username);
        localStorage.setItem("advisorFullName", advisor.fullName);

        window.location.href = "dashboard.html";
    } catch (error) {
        showLoginError("Invalid username or password.");
    }
});

function showLoginError(message) {
    loginMessage.textContent = message;
    loginMessage.className = "message error";
}