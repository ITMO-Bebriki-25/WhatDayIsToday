// admin.js — финальная версия

let allEvents = [];

const API = {
    baseUrl: 'http://localhost:8081/api/v1/events',
    get headers() {
        const token = localStorage.getItem('token');
        return {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        };
    }
};

document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('token');
    if (!token) window.location.href = 'login.html';
});

function logout() {
    localStorage.removeItem('token');
    window.location.href = 'login.html';
}

function showForm(action) {
    const formsSection = document.getElementById('forms-section');
    let formHTML = '';

    switch(action) {
        case 'add':
            formHTML = `
                <form id="add-form" onsubmit="handleAdd(event)">
                    <input type="date" name="eventDate" required>
                    <input type="text" name="name" placeholder="Название" required>
                    <input type="url" name="imageUrl" placeholder="Ссылка на изображение">
                    <input type="url" name="sourceUrl" placeholder="Ссылка на источник">
                    <textarea name="description" placeholder="Описание" required></textarea>
                    <button type="submit">Добавить</button>
                </form>
            `;
            break;
        case 'edit':
            formHTML = `
                <form id="edit-form" onsubmit="handleEdit(event)">
                    <select id="edit-select" name="id" required></select>
                    <input type="date" name="eventDate" required>
                    <input type="text" name="name" placeholder="Название" required>
                    <input type="url" name="imageUrl" placeholder="Ссылка на изображение">
                    <input type="url" name="sourceUrl" placeholder="Ссылка на источник">
                    <textarea name="description" placeholder="Описание" required></textarea>
                    <button type="submit">Обновить</button>
                </form>
            `;
            loadEventsForSelection('edit');
            break;
        case 'delete':
            formHTML = `
                <form id="delete-form" onsubmit="handleDelete(event)">
                    <select id="delete-select" name="id" required></select>
                    <button type="submit">Удалить</button>
                </form>
            `;
            loadEventsForSelection('delete');
            break;
        case 'add_admin':
            formHTML = `
                <form id="add-admin-form" onsubmit="handleAddAdmin(event)">
                    <input type="text" name="username" placeholder="Логин" required>
                    <input type="password" name="password" placeholder="Пароль" required>
                    <button type="submit">Добавить</button>
                </form>
            `;
            break;
    }

    formsSection.innerHTML = formHTML;
}

async function loadEventsForSelection(action) {
    try {
        const response = await fetch(API.baseUrl, { headers: API.headers });
        if (!response.ok) throw new Error('Ошибка загрузки событий');
        const events = await response.json();
        allEvents = events;

        const select = document.getElementById(`${action}-select`);
        select.innerHTML = events.map(event =>
            `<option value="${event.id}">${event.eventYear} - ${event.name}</option>`
        ).join('');
    } catch (err) {
        alert('Не удалось загрузить список событий.');
        console.error(err);
    }
}

function formatMonthDay(dateStr) {
    const [year, month, day] = dateStr.split('-');
    return `--${month}-${day}`;
}

async function handleAdd(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const rawData = Object.fromEntries(formData.entries());

    const [year, month, day] = rawData.eventDate.split('-');

    const data = {
        name: rawData.name,
        description: rawData.description,
        sourceUrl: rawData.sourceUrl,
        imageUrl: rawData.imageUrl,
        eventYear: year,
        eventDate: formatMonthDay(rawData.eventDate)
    };

    try {
        const response = await fetch(API.baseUrl, {
            method: 'POST',
            headers: API.headers,
            body: JSON.stringify(data)
        });

        if (!response.ok) throw new Error(await response.text());
        alert('Событие добавлено успешно!');
    } catch (err) {
        alert('Ошибка при добавлении события: ' + err.message);
    }
}

async function handleEdit(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const rawData = Object.fromEntries(formData.entries());

    const data = {
        id: Number(rawData.id),
        name: rawData.name,
        description: rawData.description,
        sourceUrl: rawData.sourceUrl,
        imageUrl: rawData.imageUrl,
        eventYear: rawData.eventDate.split('-')[0],
        eventDate: formatMonthDay(rawData.eventDate)
    };

    try {
        const response = await fetch(API.baseUrl, {
            method: 'PUT',
            headers: API.headers,
            body: JSON.stringify(data)
        });

        if (!response.ok) throw new Error(await response.text());
        alert('Событие обновлено!');
    } catch (err) {
        alert('Ошибка при обновлении события: ' + err.message);
    }
}

async function handleDelete(e) {
    e.preventDefault();
    const id = new FormData(e.target).get('id');

    try {
        const response = await fetch(`${API.baseUrl}/${id}`, {
            method: 'DELETE',
            headers: API.headers
        });

        if (!response.ok) throw new Error(await response.text());
        alert('Событие удалено!');
    } catch (err) {
        alert('Ошибка при удалении события: ' + err.message);
    }
}

async function handleAddAdmin(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const rawData = Object.fromEntries(formData.entries());

    const data = {
        username: rawData.username,
        password: rawData.password,
        role: "ADMIN"
    };

    try {
        const response = await fetch('http://localhost:8081/api/v1/registration', {
            method: 'POST',
            headers: API.headers,
            body: JSON.stringify(data)
        });

        if (!response.ok) throw new Error(await response.text());
        alert('Админ добавлен!');
    } catch (err) {
        alert('Ошибка при добавлении админа: ' + err.message);
    }
}