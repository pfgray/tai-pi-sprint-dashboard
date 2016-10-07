const getTitle = function (data) {
    return data.days_remaining + ' day(s) remaining';
};

const getDayLabels = function (data) {
    const days = _.map(data.history, function (h) {
        return new Date(h.date)
    });
    var lastDay = _.last(days);
    for (var i = 0; i < data.days_remaining; i++) {
        const inc = (lastDay.getDay() != 5) ? 1 : 3;
        const nextDay = new Date();
        nextDay.setDate(lastDay.getDate() + inc);
        days.push(nextDay);
        lastDay = nextDay;
    }
    return _.map(days, function (d) {
        return d.toLocaleString('en-US', {weekday: 'short'})
        + ' ' + d.toLocaleString('en-US', {month: '2-digit'})
        + '/' + d.toLocaleString('en-US', {day: '2-digit'});
    });
};

const renderChart = function (data) {
    document.getElementById('title').innerHTML = data.sprint;
    const chart = new Chart(document.getElementById('burndown-chart'),
        {
            label: 'test',
            type: 'line',
            data: {
                labels: getDayLabels(data),
                datasets: [{
                    label: 'Remaining',
                    data: _.map(data.history, function (h) {
                        return h.remaining;
                    })
                }, {
                    label: 'Completed',
                    data: _.map(data.history, function (h) {
                        return h.completed;
                    })
                }]
            },
            options: {
                title: {display: true, text: getTitle(data), fontSize: 24}
            }
        }
    );
};
