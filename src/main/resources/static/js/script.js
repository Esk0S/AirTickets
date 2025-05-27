// document.getElementById("dateInput")
//     .addEventListener("change",
function checkDate() {
    const input = document.getElementById("dateInput").value;
    const dateEntered = new Date(input);

    console.log(input); //e.g. 2015-11-13
    console.log(dateEntered); //e.g. Fri Nov 13 2015 00:00:00 GMT+0000 (GMT Standard Time)
}

function searchCities(inputElement, type) {
    const query = inputElement.value;
    const listId = (type === 'from') ? "fromPlaceList" : "toPlaceList";
    const url = "/tickets/searchCities?query=" + query;

    axios.get(url)
        .then(function(response) {
            // Очистить старые элементы в списке datalist
            const datalist = document.getElementById(listId);
            datalist.innerHTML = "";

            // Заполняем новый список значениями
            response.data.forEach(function(city) {
                const option = document.createElement("option");
                option.value = city;
                datalist.appendChild(option);
            });
        })
        .catch(function(error) {
            console.error("Ошибка при загрузке данных:", error);
        });
}