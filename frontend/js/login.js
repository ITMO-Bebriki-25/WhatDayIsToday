document.getElementById('login-form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());

    try {
        const response = await fetch('http://localhost:8081/api/v1/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            // Редирект на админку
            window.location.href = 'admin.html';
        } else {
            // Ошибка входа
            document.getElementById('login-error').style.display = 'block';
        }
    } catch (error) {
        console.error('Ошибка входа:', error);
        document.getElementById('login-error').textContent = 'Ошибка сервера. Попробуйте позже.';
        document.getElementById('login-error').style.display = 'block';
    }
});