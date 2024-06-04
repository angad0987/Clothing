function Weather() {
    //                 event.preventDefault();
                    var location = document.getElementById('location1').value;
                    //hitting ajax request
                    xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState === 4 && xhr.status === 200) {
                            var weatherDetail = JSON.parse(xhr.responseText);
                            updateWeatherDetails(weatherDetail);
                            //show the weather container
                            document.getElementById('weatherDetails').style.display = 'block';
                        }
                    };
                    xhr.open('GET', 'https://weatherapi-com.p.rapidapi.com/forecast.json?q=' + location, true);
                    xhr.setRequestHeader('X-RapidAPI-Key', 'd596aafb4bmsh48a1aa15638dadap198e27jsn8cc59e833153');
                    xhr.setRequestHeader('X-RapidAPI-Host', 'weatherapi-com.p.rapidapi.com');
                    xhr.send();
                    return false;
                }
    
                function updateWeatherDetails(weatherDetail) {
                    document.getElementById('condition1').value = weatherDetail.forecast.forecastday[0].day.condition.code;
                    document.getElementById('temp1').value = weatherDetail.forecast.forecastday[0].day.avgtemp_c;
                    document.getElementById('condition2').innerHTML = weatherDetail.forecast.forecastday[0].day.condition.text;
                    document.getElementById('city1').innerHTML = weatherDetail.location.name;
                    document.getElementById('temp').innerHTML = weatherDetail.current.temp_c + '&deg;C';
                    document.getElementById('humidityId').innerHTML = weatherDetail.current.humidity + '%';
                    document.getElementById('windId').innerHTML = weatherDetail.current.wind_kph + 'kph';
                    //update weather icon dynamically 
                    var weatherIcon = document.getElementById('weatherIcon');
                    var conditionCode = weatherDetail.forecast.forecastday[0].day.condition.code;
    
    //                var temperature = weatherDetail.current.temp_c;
                    var isDay = weatherDetail.current.is_day;
                    var iconMappingDay = {
                        '1000': '/Clothing/images/sunny.png',
                        '1003': '/Clothing/images/cloudy.png',
                        '1006': '/Clothing/images/cloudy.png',
                        '1009': '/Clothing/images/overcast.png',
                        '1030': '/Clothing/images/mist.png',
                        '1063': '/Clothing/images/lightRain.png',
                        '1066': '/Clothing/images/lightSnow.png',
                        '1069': '/Clothing/images/lightRain.png',
                        '1072': '/Clothing/images/coldWeather.png',
                        '1087': '/Clothing/images/coldWeather.png',
                        '1114': '/Clothing/images/drizzle.png',
                        '1117': '/Clothing/images/drizzle.png',
                        '1135': '/Clothing/images/fog.png',
                        '1147': '/Clothing/images/coldWeather.png',
                        '1150': '/Clothing/images/lightRain.png',
                        '1153': '/Clothing/images/lightRain.png',
                        '1168': '/Clothing/images/coldWeather.png',
                        '1171': '/Clothing/images/coldWeather.png',
                        '1180': '/Clothing/images/lightRain.png',
                        '1183': '/Clothing/images/lightRain.png',
                        '1186': '/Clothing/images/lightRain.png',
                        '1189': '/Clothing/images/lightRain.png',
                        '1192': '/Clothing/images/heavyRain.png',
                        '1195': '/Clothing/images/heavyRain.png',
                        '1198': '/Clothing/images/heavyRain.png',
                        '1201': '/Clothing/images/heavyFreezingRain.png',
                        '1204': '/Clothing/images/sleet.png',
                        '1207': '/Clothing/images/sleet.png',
                        '1210': '/Clothing/images/lightSnow.png',
                        '1213': '/Clothing/images/lightSnow.png',
                        '1216': '/Clothing/images/lightSnow.png',
                        '1219': '/Clothing/images/lightSnow.png',
                        '1222': '/Clothing/images/heavySnow.png',
                        '1225': '/Clothing/images/heavySnow.png',
                        '1237': '/Clothing/images/heavyFreezingRain.png',
                        '1240': '/Clothing/images/lightRain.png',
                        '1243': '/Clothing/images/heavyRain.png',
                        '1246': '/Clothing/images/heavyRain.png',
                        '1249': '/Clothing/images/sleet.png',
                        '1252': '/Clothing/images/heavySleet.png',
                        '1255': '/Clothing/images/lightSnow.png',
                        '1258': '/Clothing/images/heavySnow.png',
                        '1261': '/Clothing/images/heavyFreezingRain.png',
                        '1264': '/Clothing/images/heavyFreezingRain.png',
                        '1273': '/Clothing/images/lightRain.png',
                        '1276': '/Clothing/images/heavyRain.png',
                        '1279': '/Clothing/images/lightSnow.png',
                        '1282': '/Clothing/images/heavySnow.png'
                    };
                    
    
                    // var iconMappingDay = {
                    //     '1000': '<c:url value="/resources/images/sunny.png" />',
                    //     '1003': '<c:url value="/resources/images/cloudy.png" />',
                    //     '1006': '<c:url value="/resources/images/cloudy.png" />',
                    //     '1009': '<c:url value="/resources/images/overcast.png" />',
                    //     '1030': '<c:url value="/resources/images/mist.png" />',
                    //     '1063': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1066': '<c:url value="/resources/images/lightSnow.png" />',
                    //     '1069': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1072': '<c:url value="/resources/images/coldWeather.png" />',
                    //     '1087': '<c:url value="/resources/images/coldWeather.png" />',
                    //     '1114': '<c:url value="/resources/images/drizzle.png" />',
                    //     '1117': '<c:url value="/resources/images/drizzle.png" />',
                    //     '1135': '<c:url value="/resources/images/fog.png" />',
                    //     '1147': '<c:url value="/resources/images/coldWeather.png" />',
                    //     '1150': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1153': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1168': '<c:url value="/resources/images/coldWeather.png" />',
                    //     '1171': '<c:url value="/resources/images/coldWeather.png" />',
                    //     '1180': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1183': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1186': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1189': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1192': '<c:url value="/resources/images/heavyRain.png" />',
                    //     '1195': '<c:url value="/resources/images/heavyRain.png" />',
                    //     '1198': '<c:url value="/resources/images/heavyRain.png" />',
                    //     '1201': '<c:url value="/resources/images/heavyFreezingRain.png" />',
                    //     '1204': '<c:url value="/resources/images/sleet.png" />',
                    //     '1207': '<c:url value="/resources/images/sleet.png" />',
                    //     '1210': '<c:url value="/resources/images/lightSnow.png" />',
                    //     '1213': '<c:url value="/resources/images/lightSnow.png" />',
                    //     '1216': '<c:url value="/resources/images/lightSnow.png" />',
                    //     '1219': '<c:url value="/resources/images/lightSnow.png" />',
                    //     '1222': '<c:url value="/resources/images/heavySnow.png" />',
                    //     '1225': '<c:url value="/resources/images/heavySnow.png" />',
                    //     '1237': '<c:url value="/resources/images/heavyFreezingRain.png" />',
                    //     '1240': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1243': '<c:url value="/resources/images/heavyRain.png" />',
                    //     '1246': '<c:url value="/resources/images/heavyRain.png" />',
                    //     '1249': '<c:url value="/resources/images/sleet.png" />',
                    //     '1252': '<c:url value="/resources/images/heavySleet.png" />',
                    //     '1255': '<c:url value="/resources/images/lightSnow.png" />',
                    //     '1258': '<c:url value="/resources/images/heavySnow.png" />',
                    //     '1261': '<c:url value="/resources/images/heavyFreezingRain.png" />',
                    //     '1264': '<c:url value="/resources/images/heavyFreezingRain.png" />',
                    //     '1273': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1276': '<c:url value="/resources/images/heavyRain.png" />',
                    //     '1279': '<c:url value="/resources/images/lightSnow.png" />',
                    //     '1282': '<c:url value="/resources/images/heavySnow.png" />'
                    // };
    
                    // var iconMappingNight = {
                    //     '1000': '<c:url value="/resources/images/nightNew.png" />',
                    //     '1003': '<c:url value="/resources/images/partlyCloudyNight.png" />',
                    //     '1006': '<c:url value="/resources/images/partlyCloudyNight.png" />',
                    //     '1009': '<c:url value="/resources/images/overcast.png" />',
                    //     '1030': '<c:url value="/resources/images/mist.png" />',
                    //     '1063': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1066': '<c:url value="/resources/images/lightSnow.png" />',
                    //     '1069': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1072': '<c:url value="/resources/images/coldWeather.png" />',
                    //     '1087': '<c:url value="/resources/images/coldWeather.png" />',
                    //     '1114': '<c:url value="/resources/images/drizzle.png" />',
                    //     '1117': '<c:url value="/resources/images/drizzle.png" />',
                    //     '1135': '<c:url value="/resources/images/fog.png" />',
                    //     '1147': '<c:url value="/resources/images/coldWeather.png" />',
                    //     '1150': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1153': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1168': '<c:url value="/resources/images/coldWeather.png" />',
                    //     '1171': '<c:url value="/resources/images/coldWeather.png" />',
                    //     '1180': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1183': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1186': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1189': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1192': '<c:url value="/resources/images/heavyRain.png" />',
                    //     '1195': '<c:url value="/resources/images/heavyRain.png" />',
                    //     '1198': '<c:url value="/resources/images/heavyRain.png" />',
                    //     '1201': '<c:url value="/resources/images/heavyFreezingRain.png" />',
                    //     '1204': '<c:url value="/resources/images/sleet.png" />',
                    //     '1207': '<c:url value="/resources/images/sleet.png" />',
                    //     '1210': '<c:url value="/resources/images/lightSnow.png" />',
                    //     '1213': '<c:url value="/resources/images/lightSnow.png" />',
                    //     '1216': '<c:url value="/resources/images/lightSnow.png" />',
                    //     '1219': '<c:url value="/resources/images/lightSnow.png" />',
                    //     '1222': '<c:url value="/resources/images/heavySnow.png" />',
                    //     '1225': '<c:url value="/resources/images/heavySnow.png" />',
                    //     '1237': '<c:url value="/resources/images/heavyFreezingRain.png" />',
                    //     '1240': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1243': '<c:url value="/resources/images/heavyRain.png" />',
                    //     '1246': '<c:url value="/resources/images/heavyRain.png" />',
                    //     '1249': '<c:url value="/resources/images/sleet.png" />',
                    //     '1252': '<c:url value="/resources/images/heavySleet.png" />',
                    //     '1255': '<c:url value="/resources/images/lightSnow.png" />',
                    //     '1258': '<c:url value="/resources/images/heavySnow.png" />',
                    //     '1261': '<c:url value="/resources/images/heavyFreezingRain.png" />',
                    //     '1264': '<c:url value="/resources/images/heavyFreezingRain.png" />',
                    //     '1273': '<c:url value="/resources/images/lightRain.png" />',
                    //     '1276': '<c:url value="/resources/images/heavyRain.png" />',
                    //     '1279': '<c:url value="/resources/images/lightSnow.png" />',
                    //     '1282': '<c:url value="/resources/images/heavySnow.png" />'
                    // };
                    var iconMappingNight = {
                        '1000': '/Clothing/images/nightNew.png',
                        '1003': '/Clothing/images/partlyCloudyNight.png',
                        '1006': '/Clothing/images/partlyCloudyNight.png',
                        '1009': '/Clothing/images/overcast.png',
                        '1030': '/Clothing/images/mist.png',
                        '1063': '/Clothing/images/lightRain.png',
                        '1066': '/Clothing/images/lightSnow.png',
                        '1069': '/Clothing/images/lightRain.png',
                        '1072': '/Clothing/images/coldWeather.png',
                        '1087': '/Clothing/images/coldWeather.png',
                        '1114': '/Clothing/images/drizzle.png',
                        '1117': '/Clothing/images/drizzle.png',
                        '1135': '/Clothing/images/fog.png',
                        '1147': '/Clothing/images/coldWeather.png',
                        '1150': '/Clothing/images/lightRain.png',
                        '1153': '/Clothing/images/lightRain.png',
                        '1168': '/Clothing/images/coldWeather.png',
                        '1171': '/Clothing/images/coldWeather.png',
                        '1180': '/Clothing/images/lightRain.png',
                        '1183': '/Clothing/images/lightRain.png',
                        '1186': '/Clothing/images/lightRain.png',
                        '1189': '/Clothing/images/lightRain.png',
                        '1192': '/Clothing/images/heavyRain.png',
                        '1195': '/Clothing/images/heavyRain.png',
                        '1198': '/Clothing/images/heavyRain.png',
                        '1201': '/Clothing/images/heavyFreezingRain.png',
                        '1204': '/Clothing/images/sleet.png',
                        '1207': '/Clothing/images/sleet.png',
                        '1210': '/Clothing/images/lightSnow.png',
                        '1213': '/Clothing/images/lightSnow.png',
                        '1216': '/Clothing/images/lightSnow.png',
                        '1219': '/Clothing/images/lightSnow.png',
                        '1222': '/Clothing/images/heavySnow.png',
                        '1225': '/Clothing/images/heavySnow.png',
                        '1237': '/Clothing/images/heavyFreezingRain.png',
                        '1240': '/Clothing/images/lightRain.png',
                        '1243': '/Clothing/images/heavyRain.png',
                        '1246': '/Clothing/images/heavyRain.png',
                        '1249': '/Clothing/images/sleet.png',
                        '1252': '/Clothing/images/heavySleet.png',
                        '1255': '/Clothing/images/lightSnow.png',
                        '1258': '/Clothing/images/heavySnow.png',
                        '1261': '/Clothing/images/heavyFreezingRain.png',
                        '1264': '/Clothing/images/heavyFreezingRain.png',
                        '1273': '/Clothing/images/lightRain.png',
                        '1276': '/Clothing/images/heavyRain.png',
                        '1279': '/Clothing/images/lightSnow.png',
                        '1282': '/Clothing/images/heavySnow.png'
                    };
                    
    
    
                    if (isDay === 1) {
    
                        var matchedIconMapping = iconMappingDay;
                    } else {
                        var matchedIconMapping = iconMappingNight;
                    }
    
                    if (matchedIconMapping[conditionCode]) {
                        weatherIcon.src = matchedIconMapping[conditionCode];
                    }
    
                }
    
    
    
    
                function submitForm() {
                    // Check if either male or female button is selected
                    var maleSelected = document.querySelector('.btn-male').classList.contains('selected');
                    var femaleSelected = document.querySelector('.btn-female').classList.contains('selected');
    
                    var gender = document.getElementById('gender');
                    if (maleSelected) {
                        gender.value = 'Male';
                    } else if (femaleSelected) {
                        gender.value = 'Female';
                    } else {
                        Swal.fire({
                            title: 'Choose Gender !',
                            text: 'Select gender is Required',
                            icon: 'success',
                            timer: 2000,
                            timerProgressBar: true,
                            showConfirmButton: false
                        });
                        return;
                    }
                    document.getElementById('form').submit();
                }
                function toggleGender(button) {
                    // Remove 'selected' class from all gender buttons
                    document.querySelectorAll('.btn-male').forEach(function (btn) {
                        btn.classList.remove('selected');
                    });
                       document.querySelectorAll('.btn-female').forEach(function (btn) {
                        btn.classList.remove('selected');
                    });
    
                    // Add 'selected' class to the clicked button
                    button.classList.add('selected');
                    console.log(button.classList.contains('selected'));
                }

                document.addEventListener('DOMContentLoaded', function(){
                    var bodyElement = document.body;
                    var baseUrl = document.getElementById('baseUrl').getAttribute('data-itemid');
                    if (bodyElement) {
                      console.log(baseUrl);
                      bodyElement.style.backgroundImage = 'url(' + baseUrl + '/images/bluewater.jpg)';
                    }
                 
                });
    
    
    