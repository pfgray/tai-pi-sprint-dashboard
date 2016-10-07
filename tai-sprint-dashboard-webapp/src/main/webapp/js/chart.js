const req = new XMLHttpRequest();
req.open('GET', '/js/samples/current-sprint.json', true);
req.send(null);

const getTitle = function(data) {
    return data.sprint + ' -- ' +  data.days_remaining + ' day(s) remaining';
};

const getDayLabels = function(data) {
    const days = _.map(data.history, function(h) { return new Date(h.date) });
    var lastDay = _.last(days);
    for (var i = 0; i < data.days_remaining; i++) {
        var inc = (lastDay.getDay() != 5) ? 1 : 3;
        const nextDay = new Date();
        nextDay.setDate(lastDay.getDate() + inc);
        days.push(nextDay);
        lastDay = nextDay;
    }
    return _.map(days, function(d) { return d.toLocaleString('en-US', { weekday: 'short' })});
};

const renderChart = function(data) {
    const chart = new Chart(document.getElementById('burndown-chart'),
        {
            label: 'test',
            type: 'line',
            data: {
                labels: getDayLabels(data),
                datasets: [{
                    label: 'Remaining: ' + data.remaining,
                    data: _.map(data.history, function(h) { return h.remaining; })
                },{
                    label: 'Completed: ' + data.completed,
                    data: _.map(data.history, function(h) { return h.completed; })
                }]
            },
            options: {
                title: {display: true, text: getTitle(data), fontSize: 24}
            }
        }
    );
};

req.onreadystatechange = function () {
    if (req.readyState == 4 && req.status == 200) {
        renderChart(JSON.parse(req.responseText));
    }
};
