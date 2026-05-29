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
        const response = await fetch("http://localhost:8080/api/v1/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        if (!response.ok) {
            showLoginError("Invalid username or password.");
            return;
        }

        const loginResponse = await response.json();

        localStorage.setItem("advisorId", loginResponse.advisorId);
        localStorage.setItem("advisorUsername", loginResponse.username);
        localStorage.setItem("advisorFullName", loginResponse.fullName);
        localStorage.setItem("loginNotifications", JSON.stringify(loginResponse.notifications || []));

        window.location.href = "dashboard.html";
    } catch (error) {
        showLoginError("Could not connect to the server.");
    }
});

function showLoginError(message) {
    loginMessage.textContent = message;
    loginMessage.className = "message error";
}