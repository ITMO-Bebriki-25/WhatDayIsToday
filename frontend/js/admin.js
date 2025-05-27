let allEvents = [];

// Базовая конфигурация API
const API = {
    baseUrl: '/api/events',
    headers: {
        'Content-Type': 'application/json'
    }
};

// Показать соответствующую форму
function showForm(action) {
    const formsSection = document.getElementById('forms-section');
    let formHTML = '';

    switch(action) {
        case 'add':
            formHTML = `
                <form id="add-form" onsubmit="handleAdd(event)">
                    <div class="form-group">
                        <label>Дата события</label>
                        <input type="date" name="event-date" required 
                            pattern="\d{4}-\d{2}-\d{2}"
                            min="1890-01-01" max="2000-12-31">
                    </div>
                    <input type="text" name="name" placeholder="Название" required>
                    <input type="url" name="image_url" placeholder="Ссылка на изображение">
                    <input type="url" name="source_url" placeholder="Ссылка на источник">
                    <div></div>
                    <textarea name="description" placeholder="Описание" required></textarea>
                    <div></div>
                    <button type="submit">Добавить</button>
                </form>
            `;
            break;
            
        case 'edit':
            formHTML = `
                <form id="edit-form" onsubmit="handleEdit(event)">
                    <select id="edit-select" name="id" required></select>
                    <!-- Поля аналогичные добавлению -->
                    <button type="submit">Обновить</button>
                </form>
            `;
            break;
            
        case 'delete':
            formHTML = `
                <form id="delete-form" onsubmit="handleDelete(event)">
                    <select id="delete-select" name="id" required></select>
                    <button type="submit">Удалить</button>
                </form>
            `;
            break;
        case 'add_admin':
            formHTML = `
                <form id="add-form" onsubmit="handleAdd(event)">
                    <input type="text" name="login" placeholder="Логин" required>
                    <input type="password" name="password" placeholder="Пароль" required>
                    <button type="submit">Добавить</button>
                </form>
            `;
            break;
    }
    
    formsSection.innerHTML = formHTML;
    if (action === 'edit' || action === 'delete') {
        loadEventsForSelection();
    }
}

// Загрузка событий для выбора
async function loadEventsForSelection() {
    const response = await fetch(API.baseUrl);
    allEvents = await response.json();
    
    const select = document.getElementById(`${action}-select`);
    select.innerHTML = allEvents.map(event => 
        `<option value="${event.id}">${event.year} - ${event.name}</option>`
    ).join('');
}

// Обработчики действий
async function handleAdd(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());
    
    await fetch(API.baseUrl, {
        method: 'POST',
        headers: API.headers,
        body: JSON.stringify(data)
    });
    // Обновляем список
}

// Аналогичные функции для handleEdit и handleDelete