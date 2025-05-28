// admin.js — финальная версия

let allEvents = [];

const config = {
    baseUrl: '/api/v1',
    get headers() {
        return {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    }
};

document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('token');
    const expiry = Date.parse(localStorage.getItem('tokenExpires'));
    if (!token || expiry < Date.now()) {
        logout();
    }
});

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('tokenExpires');
    window.location.href = 'login.html';
}

// Форматирование даты для отображения
function formatDateForDisplay(monthDay, year) {
    if (!monthDay) return '';
    const [month, day] = monthDay.split('-').slice(2);
    const months = [
        'января', 'февраля', 'марта', 'апреля', 'мая', 'июня',
        'июля', 'августа', 'сентября', 'октября', 'ноября', 'декабря'
    ];
    return `${day} ${months[parseInt(month) - 1]} ${year}`;
}

// Преобразование MonthDay в формат yyyy-MM-dd
function monthDayToFullDate(monthDay, year) {
    if (!monthDay || !year) return '';
    const [_, __, month, day] = monthDay.split('-');
    return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;
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
                    <select id="edit-select" name="id" required onchange="fillEditForm(this.value)"></select>
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

// Заполнение формы редактирования данными выбранного события
function fillEditForm(eventId) {
    const event = allEvents.find(e => e.id === parseInt(eventId));
    if (!event) return;

    const form = document.getElementById('edit-form');
    if (!form) return;

    form.elements.eventDate.value = monthDayToFullDate(event.eventDate, event.eventYear);
    form.elements.name.value = event.name || '';
    form.elements.imageUrl.value = event.imageUrl || '';
    form.elements.sourceUrl.value = event.sourceUrl || '';
    form.elements.description.value = event.description || '';
}

async function loadEventsForSelection(action) {
    try {
        const response = await fetch(`${config.baseUrl}/events`, { headers: config.headers });
        if (!response.ok) throw new Error('Ошибка загрузки событий');
        const events = await response.json();
        allEvents = events;

        const select = document.getElementById(`${action}-select`);
        select.innerHTML = events.map(event =>
            `<option value="${event.id}">${formatDateForDisplay(event.eventDate, event.eventYear)} - ${event.name}</option>`
        ).join('');

        // Автоматически заполняем форму при загрузке событий для редактирования
        if (action === 'edit' && events.length > 0) {
            fillEditForm(events[0].id);
        }
    } catch (err) {
        alert('Не удалось загрузить список событий.');
        console.error(err);
    }
}

function formatMonthDay(dateStr) {
    const [__, month, day] = dateStr.split('-');
    return `--${month}-${day}`;
}

async function handleAdd(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const rawData = Object.fromEntries(formData.entries());

    const [year, _, __] = rawData.eventDate.split('-');

    const data = {
        name: rawData.name,
        description: rawData.description,
        sourceUrl: rawData.sourceUrl,
        imageUrl: rawData.imageUrl,
        eventYear: year,
        eventDate: formatMonthDay(rawData.eventDate)
    };

    try {
        const response = await fetch(`${config.baseUrl}/events`, {
            method: 'POST',
            headers: config.headers,
            body: JSON.stringify(data)
        });

        if (!response.ok) throw new Error(await response.text());
        alert('Событие добавлено успешно!');
        e.target.reset();
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
        const response = await fetch(`${config.baseUrl}/events`, {
            method: 'PUT',
            headers: config.headers,
            body: JSON.stringify(data)
        });

        if (!response.ok) throw new Error(await response.text());
        alert('Событие обновлено!');
        // Перезагружаем список событий после успешного обновления
        loadEventsForSelection('edit');
    } catch (err) {
        alert('Ошибка при обновлении события: ' + err.message);
    }
}

async function handleDelete(e) {
    e.preventDefault();
    const id = new FormData(e.target).get('id');

    try {
        const response = await fetch(`${config.baseUrl}/events/${id}`, {
            method: 'DELETE',
            headers: config.headers
        });

        if (!response.ok) throw new Error(await response.text());
        alert('Событие удалено!');
        // Перезагружаем список событий после успешного удаления
        loadEventsForSelection('delete');
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
        const response = await fetch(`${config.baseUrl}/registration`, {
            method: 'POST',
            headers: config.headers,
            body: JSON.stringify(data)
        });

        if (!response.ok) throw new Error(await response.text());
        alert('Админ добавлен!');
        e.target.reset();
    } catch (err) {
        alert('Ошибка при добавлении админа: ' + err.message);
    }
}