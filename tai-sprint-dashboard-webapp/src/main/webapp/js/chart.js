const req = new XMLHttpRequest();
req.open('GET', '/js/samples/current-sprint.json', true);
req.send(null);

const getTitle = function(data) {
    return data.sprint + ' -- completed:' + data.completed + ', remaining: ' + data.remaining;
};

const renderChart = function(data) {
    const chart = new Chart(document.getElementById('burndown-chart'),
        {
            type: 'bar',
            data: {
                labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
                datasets: [{
                    label: '# of Votes',
                    data: [12, 19, 3, 5, 2, 3],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255,99,132,1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero:true
                        }
                    }]
                }
            }
        }
        /*
        {
        //label: getTitle(data),
        data:{
            labels: ["January", "February", "March", "April", "May", "June", "July"],
            datasets: [
                {
                    label: "My First dataset",
                    fill: false,
                    lineTension: 0.1,
                    backgroundColor: "rgba(75,192,192,0.4)",
                    borderColor: "rgba(75,192,192,1)",
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    pointBorderColor: "rgba(75,192,192,1)",
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 1,
                    pointHoverRadius: 5,
                    pointHoverBackgroundColor: "rgba(75,192,192,1)",
                    pointHoverBorderColor: "rgba(220,220,220,1)",
                    pointHoverBorderWidth: 2,
                    pointRadius: 1,
                    pointHitRadius: 10,
                    data: [65, 59, 80, 81, 56, 55, 40],
                    spanGaps: false,
                }
            ]
        data: {
            labels: ['Wed', 'Thur', 'Fri', 'Mon', 'Tue', 'Wed', 'Thur', 'Fri', 'Mon', 'Tue'],
            datasets: [
                {
                    type: 'line',
                    label: 'remaining',
                    data: []
                },
                {
                    type: 'line',
                    label: 'completed',
                    data: []
                }

            ]
        }
    }
         */
    );
};

req.onreadystatechange = function () {
    if (req.readyState == 4 && req.status == 200) {
        renderChart(JSON.parse(req.responseText));
    }
};
