// main.js — отображение событий на главной странице

document.addEventListener('DOMContentLoaded', function() {
    // Инициализация Flatpickr календаря
    const datePicker = flatpickr("#date-picker", {
        dateFormat: "d.m.Y",
        locale: "ru",
        defaultDate: new Date(),
        onChange: function(selectedDates) {
            if (selectedDates.length > 0) {
                updateCurrentDate(selectedDates[0]);
                loadEvents(selectedDates[0]);
            }
        }
    });

    // Функция форматирования даты
    function formatDate(date) {
        const options = { day: 'numeric', month: 'long', year: 'numeric' };
        return date.toLocaleDateString('ru-RU', options);
    }

    // Обновление отображаемой даты
    function updateCurrentDate(date) {
        document.getElementById('current-date').textContent = formatDate(date);
    }

    // Загрузка событий для выбранной даты
    function loadEvents(date) {
        const month = date.getMonth() + 1; // JavaScript months are 0-based
        const day = date.getDate();
        
        fetch(`http://89.104.71.156:8000/api/v1/events?month=${month}&day=${day}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(events => {
                displayEvents(events);
            })
            .catch(error => {
                console.error('Ошибка при загрузке событий:', error);
                document.getElementById('events-container').innerHTML = `
                    <p class="error-message">
                        Ошибка загрузки данных. Попробуйте позже.
                    </p>`;
            });
    }

    // Отображение событий
    function displayEvents(events) {
        const container = document.getElementById('events-container');
        if (!events || events.length === 0) {
            container.innerHTML = '<p class="no-events">На эту дату событий не найдено</p>';
            return;
        }

        container.innerHTML = events.map(event => `
            <div class="event-card">
                <div class="event-header">
                    <span class="event-year">${event.eventYear ? event.eventYear : ''}</span>
                    <h2>${event.name}</h2>
                </div>
                <div class="event-content">
                    <p>${event.description}</p>
                    ${event.imageUrl ? `<img src="${event.imageUrl}" alt="${event.name}">` : ''}
                    ${event.sourceUrl ? `
                        <div class="source-link-container">
                            <a href="${event.sourceUrl}" class="source-link" target="_blank">
                                Читать первоисточник →
                            </a>
                        </div>
                    ` : ''}
                </div>
            </div>
        `).join('');
    }

    // Функция для изменения даты
    function changeDate(days) {
        const currentDate = datePicker.selectedDates[0] || new Date();
        const newDate = new Date(currentDate);
        newDate.setDate(newDate.getDate() + days);
        datePicker.setDate(newDate, true); // true для вызова onChange
    }

    // Обработчики для кнопок навигации
    document.querySelector('.prev-day').addEventListener('click', () => {
        changeDate(-1);
    });

    document.querySelector('.next-day').addEventListener('click', () => {
        changeDate(1);
    });

    // Обработчики для быстрой навигации
    document.querySelectorAll('.quick-nav-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            if (btn.classList.contains('today')) {
                datePicker.setDate(new Date(), true);
            } else {
                const days = parseInt(btn.dataset.days) || 0;
                changeDate(days);
            }
        });
    });

    // Инициализация с текущей датой
    const today = new Date();
    datePicker.setDate(today, true);
});