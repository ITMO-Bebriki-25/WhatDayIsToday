document.addEventListener('DOMContentLoaded', () => {
    const today = new Date();
    const day = today.getDate();
    const monthNumber = today.getMonth() + 1; // Для запроса
    
    // Форматирование месяца в правильном падеже
    const monthNames = {
        1: 'Января', 2: 'Февраля', 3: 'Марта', 
        4: 'Апреля', 5: 'Мая', 6: 'Июня',
        7: 'Июля', 8: 'Августа', 9: 'Сентября',
        10: 'Октября', 11: 'Ноября', 12: 'Декабря'
    };
    
    const formattedDate = `${day} ${monthNames[monthNumber]}`;
    document.getElementById('current-date').textContent = formattedDate;
    
    // Делаем запрос с числовыми параметрами
    
    fetch(`http://localhost:8081/api/v1/events?month=${monthNumber}&day=${day}`, {
           method: 'GET',
        })
        .then(response => {
            if (!response.ok) throw new Error('Ошибка сети');
            return response.json();
        })
        .then(events => {
            const container = document.getElementById('events-container');
            container.innerHTML = ''; 
            
            if (!events || events.length === 0) {
                container.innerHTML = `
                    <p class="no-events">
                        На сегодняшнюю дату нет никаких событий,<br>
                        но возможно они появятся в будущем :)
                    </p>
                `;
                return;
            }
            
            events.forEach(event => {
                const eventHTML = `
                    <div class="event-card">
                        <div class="event-header">
                            <span class="event-year">${formattedDate + " " + event.year}</span>
                            <h2>${event.name}</h2>
                        </div>
                        ${event.image_url ? `<img src="${event.image_url}" alt="${event.name}">` : ''}
                        <div class="event-content">
                            <p>${event.description}</p>
                            ${event.source_url ? `
                            <div class="source-link-container">
                                <a href="${event.source_url}" class="source-link" target="_blank">
                                    Читать первоисточник →
                                </a>
                            </div>` : ''}
                        </div>
                    </div>
                `;
                container.insertAdjacentHTML('beforeend', eventHTML);
            });
        })
        .catch(error => {
            console.error('Ошибка:', error);
            document.getElementById('events-container').innerHTML = `
                <p class="error-message">
                    Произошла ошибка при загрузке событий. Пожалуйста, попробуйте позже.
                </p>
            `;
        });
});