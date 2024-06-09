

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
    //                document.getElementById('condition1').value = weatherDetail.forecast.forecastday[0].day.condition.code;
    //                document.getElementById('temp1').value = weatherDetail.forecast.forecastday[0].day.avgtemp_c;
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
                    console.log(isDay);
                    var baseUrl = document.getElementById('baseUrl').getAttribute('data-itemid');
                    console.log('base url :'+baseUrl);
                    var iconMappingDay = {
                        '1000': baseUrl+'/images/sunny.png',
                        '1003': baseUrl+'/images/cloudy.png',
                        '1006': baseUrl+'/images/cloudy.png',
                        '1009': baseUrl+'/images/overcast.png',
                        '1030': baseUrl+'/images/mist.png',
                        '1063': baseUrl+'/images/lightRain.png',
                        '1066': baseUrl+'/images/lightSnow.png',
                        '1069': baseUrl+'/images/lightRain.png',
                        '1072': baseUrl+'/images/coldWeather.png',
                        '1087': baseUrl+'/images/coldWeather.png',
                        '1114': baseUrl+'/images/drizzle.png',
                        '1117': baseUrl+'/images/drizzle.png',
                        '1135': baseUrl+'/images/fog.png',
                        '1147': baseUrl+'/images/coldWeather.png',
                        '1150': baseUrl+'/images/lightRain.png',
                        '1153': baseUrl+'/images/lightRain.png',
                        '1168': baseUrl+'/images/coldWeather.png',
                        '1171': baseUrl+'/images/coldWeather.png',
                        '1180': baseUrl+'/images/lightRain.png',
                        '1183': baseUrl+'/images/lightRain.png',
                        '1186': baseUrl+'/images/lightRain.png',
                        '1189': baseUrl+'/images/lightRain.png',
                        '1192': baseUrl+'/images/heavyRain.png',
                        '1195': baseUrl+'/images/heavyRain.png',
                        '1198': baseUrl+'/images/heavyRain.png',
                        '1201': baseUrl+'/images/heavyFreezingRain.png',
                        '1204': baseUrl+'/images/sleet.png',
                        '1207': baseUrl+'/images/sleet.png',
                        '1210': baseUrl+'/images/lightSnow.png',
                        '1213': baseUrl+'/images/lightSnow.png',
                        '1216': baseUrl+'/images/lightSnow.png',
                        '1219': baseUrl+'/images/lightSnow.png',
                        '1222': baseUrl+'/images/heavySnow.png',
                        '1225': baseUrl+'/images/heavySnow.png',
                        '1237': baseUrl+'/images/heavyFreezingRain.png',
                        '1240': baseUrl+'/images/lightRain.png',
                        '1243': baseUrl+'/images/heavyRain.png',
                        '1246': baseUrl+'/images/heavyRain.png',
                        '1249': baseUrl+'/images/sleet.png',
                        '1252': baseUrl+'/images/heavySleet.png',
                        '1255': baseUrl+'/images/lightSnow.png',
                        '1258': baseUrl+'/images/heavySnow.png',
                        '1261': baseUrl+'/images/heavyFreezingRain.png',
                        '1264': baseUrl+'/images/heavyFreezingRain.png',
                        '1273': baseUrl+'/images/lightRain.png',
                        '1276': baseUrl+'/images/heavyRain.png',
                        '1279': baseUrl+'/images/lightSnow.png',
                        '1282': baseUrl+'/images/heavySnow.png'
                    };
    
                    var iconMappingNight = {
                        '1000': baseUrl+'/images/nightNew.png',
                        '1003': baseUrl+'/images/partlyCloudyNight.png',
                        '1006': baseUrl+'/images/partlyCloudyNight.png',
                        '1009': baseUrl+'/images/overcast.png',
                        '1030': baseUrl+'/images/mist.png',
                        '1063': baseUrl+'/images/lightRain.png',
                        '1066': baseUrl+'/images/lightSnow.png',
                        '1069': baseUrl+'/images/lightRain.png',
                        '1072': baseUrl+'/images/coldWeather.png',
                        '1087': baseUrl+'/images/coldWeather.png',
                        '1114': baseUrl+'/images/drizzle.png',
                        '1117': baseUrl+'/images/drizzle.png',
                        '1135': baseUrl+'/images/fog.png',
                        '1147': baseUrl+'/images/coldWeather.png',
                        '1150': baseUrl+'/images/lightRain.png',
                        '1153': baseUrl+'/images/lightRain.png',
                        '1168': baseUrl+'/images/coldWeather.png',
                        '1171': baseUrl+'/images/coldWeather.png',
                        '1180': baseUrl+'/images/lightRain.png',
                        '1183': baseUrl+'/images/lightRain.png',
                        '1186': baseUrl+'/images/lightRain.png',
                        '1189': baseUrl+'/images/lightRain.png',
                        '1192': baseUrl+'/images/heavyRain.png',
                        '1195': baseUrl+'/images/heavyRain.png',
                        '1198': baseUrl+'/images/heavyRain.png',
                        '1201': baseUrl+'/images/heavyFreezingRain.png',
                        '1204': baseUrl+'/images/sleet.png',
                        '1207': baseUrl+'/images/sleet.png',
                        '1210': baseUrl+'/images/lightSnow.png',
                        '1213': baseUrl+'/images/lightSnow.png',
                        '1216': baseUrl+'/images/lightSnow.png',
                        '1219': baseUrl+'/images/lightSnow.png',
                        '1222': baseUrl+'/images/heavySnow.png',
                        '1225': baseUrl+'/images/heavySnow.png',
                        '1237': baseUrl+'/images/heavyFreezingRain.png',
                        '1240': baseUrl+'/images/lightRain.png',
                        '1243': baseUrl+'/images/heavyRain.png',
                        '1246': baseUrl+'/images/heavyRain.png',
                        '1249': baseUrl+'/images/sleet.png',
                        '1252': baseUrl+'/images/heavySleet.png',
                        '1255': baseUrl+'/images/lightSnow.png',
                        '1258': baseUrl+'/images/heavySnow.png',
                        '1261': baseUrl+'/images/heavyFreezingRain.png',
                        '1264': baseUrl+'/images/heavyFreezingRain.png',
                        '1273': baseUrl+'/images/lightRain.png',
                        '1276': baseUrl+'/images/heavyRain.png',
                        '1279': baseUrl+'/images/lightSnow.png',
                        '1282': baseUrl+'/images/heavySnow.png'
                    };
    
    
                    if (isDay === 1) {
    
                        var matchedIconMapping = iconMappingDay;
                    } else {
                        var matchedIconMapping = iconMappingNight;
                    }
    
                    if (matchedIconMapping[conditionCode]) {
                        weatherIcon.src = matchedIconMapping[conditionCode];
                    }
                    document.getElementById('weatherDetails').classList.add('show');
                    document.getElementById('weatherDetails').classList.remove('hide');
    
                }
    
                function inputBased() {
                    var value = document.getElementById('location1').value;
                    if (value.trim() === "") {
                        document.getElementById('weatherDetails').classList.add('hide');
    
                    }
    
    
                }
    
    
    
    