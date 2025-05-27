// main.js — отображение событий на главной странице

document.addEventListener('DOMContentLoaded', async () => {
    const today = new Date();
    const day = today.getDate();
    const month = today.getMonth() + 1;

    const monthNames = {
        1: 'Января', 2: 'Февраля', 3: 'Марта',
        4: 'Апреля', 5: 'Мая', 6: 'Июня',
        7: 'Июля', 8: 'Августа', 9: 'Сентября',
        10: 'Октября', 11: 'Ноября', 12: 'Декабря'
    };

    const formattedDate = `${day} ${monthNames[month]}`;
    document.getElementById('current-date').textContent = formattedDate;

    try {
        const response = await fetch(`http://localhost:8081/api/v1/events?month=${month}&day=${day}`);
        if (!response.ok) throw new Error('Ошибка загрузки событий');

        const events = await response.json();
        const container = document.getElementById('events-container');
        container.innerHTML = '';

        if (!events || events.length === 0) {
            container.innerHTML = `
                <p class="no-events">
                    На сегодняшнюю дату нет никаких событий, но возможно они появятся в будущем :)
                </p>`;
            return;
        }

        events.forEach(event => {
            const eventHTML = `
                <div class="event-card">
                    <div class="event-header">
                        <span class="event-year">${formattedDate} ${event.eventYear}</span>
                        <h2>${event.name}</h2>
                    </div>
                    ${event.imageUrl ? `<img src="${event.imageUrl}" alt="${event.name}">` : ''}
                    <div class="event-content">
                        <p>${event.description}</p>
                        ${event.sourceUrl ? `<div class="source-link-container"><a href="${event.sourceUrl}" class="source-link" target="_blank">Читать первоисточник →</a></div>` : ''}
                    </div>
                </div>
            `;
            container.insertAdjacentHTML('beforeend', eventHTML);
        });
    } catch (err) {
        console.error(err);
        document.getElementById('events-container').innerHTML = `
            <p class="error-message">
                Ошибка загрузки данных. Попробуйте позже.
            </p>`;
    }
});