const API_BASE_URL = "http://localhost:8080/api/v1";

async function apiGet(path) {
    const response = await fetch(`${API_BASE_URL}${path}`);

    if (!response.ok) {
        throw new Error(`GET ${path} failed with status ${response.status}`);
    }

    return response.json();
}

async function apiPost(path, body = null) {
    const options = {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    };

    if (body !== null) {
        options.body = JSON.stringify(body);
    }

    const response = await fetch(`${API_BASE_URL}${path}`, options);

    if (!response.ok) {
        throw new Error(`POST ${path} failed with status ${response.status}`);
    }

    return response.json();
}

async function apiPut(path, body) {
    const response = await fetch(`${API_BASE_URL}${path}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    });

    if (!response.ok) {
        throw new Error(`PUT ${path} failed with status ${response.status}`);
    }

    return response.json();
}