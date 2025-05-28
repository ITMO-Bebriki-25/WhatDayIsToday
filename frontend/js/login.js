// login.js — вход и сохранение токена

const API = {
    baseUrl: 'http://localhost:8000/api/v1',
    headers: {
        'Content-Type': 'application/json'
    }
};

document.getElementById('login-form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());

    try {
        const response = await fetch(`${API.baseUrl}/login`, {
            method: 'POST',
            headers: API.headers,
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            const errorText = await response.text();
            document.getElementById('login-error').textContent = 'Ошибка входа: ' + errorText;
            document.getElementById('login-error').style.display = 'block';
            return;
        }

        const result = await response.json();
        localStorage.setItem('token', result.token);
        window.location.href = 'admin.html';
    } catch (error) {
        document.getElementById('login-error').textContent = 'Ошибка сервера. Попробуйте позже.';
        document.getElementById('login-error').style.display = 'block';
        console.error('Ошибка входа:', error);
    }
});