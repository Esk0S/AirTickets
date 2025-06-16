function searchCities(inputId, datalistId, searchCitiesUrl) {
    const inputElement = document.getElementById(inputId);
    const datalistElement = document.getElementById(datalistId);
    if (inputElement.value.length > 1) {
        axios.get(searchCitiesUrl, { params: { query: inputElement.value } })
            .then(function (response) {
                datalistElement.innerHTML = response.data;
            })
            .catch(function (error) {
                console.error('Ошибка при получении данных: ', error);
            });
    } else {
        datalistElement.innerHTML = '';
    }
}

function getTicketsTable(searchTicketsUrl) {
    const fromPlaceElement = document.getElementById('fromPlace');
    const toPlaceElement = document.getElementById('toPlace');
    const whenElement = document.getElementById('dateInput');
    const ticketsTableElement = document.getElementById('ticketsTable');

    axios.get(searchTicketsUrl, {
        params: {
            fromPlace: fromPlaceElement.value,
            toPlace: toPlaceElement.value,
            when: whenElement.value,
        }
    })
        .then(function (response) {
            ticketsTableElement.innerHTML = response.data;
        })
        .catch(function (error) {
            console.error('Ошибка при получении данных: ', error);
        });
}

function formatPassport(input) {
    let value = input.value.replace(/\D/g, '');
    if (value.length <= 4) {
        input.value = value.replace(/(\d{1,4})/, '$1');
    } else if (value.length <= 10) {
        input.value = value.replace(/(\d{4})(\d{1,6})/, '$1 $2');
    } else {
        input.value = value.replace(/(\d{4})(\d{6})/, '$1 $2');
    }
}

function getTicketDetails(ticketDetailsUrl) {
    const ticketDetails = document.getElementById('ticketDetails');

    axios.get(ticketDetailsUrl)
        .then(function (response) {
            ticketDetails.innerHTML = response.data;
        })
        .catch(function (error) {
            console.error('Ошибка при получении данных: ', error);
        });
}