
document.addEventListener("DOMContentLoaded", () => {
    const html = document.documentElement;

    // Chart colors for light/dark mode
    function getChartColors() {
        return html.getAttribute('data-bs-theme') === 'dark'
            ? { gpa: 'rgba(13,110,253,0.9)', enroll: 'rgba(255,193,7,0.9)' }
            : { gpa: 'rgba(13,110,253,0.7)', enroll: 'rgba(255,193,7,0.5)' };
    }

    const colors = getChartColors();

    // Student Performance (Bar Chart)
    const ctx1 = document.getElementById('studentPerformanceChart').getContext('2d');
    new Chart(ctx1, {
        type: 'bar',
        data: {
            labels: ['Alice', 'Bob', 'Charlie', 'David', 'Eva'],
            datasets: [{
                label: 'GPA',
                data: [3.8, 3.2, 3.5, 3.9, 3.6],
                backgroundColor: colors.gpa,
                borderRadius: 8
            }]
        },
        options: {
            responsive: true,
            plugins: { legend: { display: false } },
            scales: { y: { beginAtZero: true, max: 4 } }
        }
    });

    // Course Analytics (Line Chart)
    const ctx2 = document.getElementById('courseAnalyticsChart').getContext('2d');
    new Chart(ctx2, {
        type: 'line',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
            datasets: [{
                label: 'Enrollments',
                data: [50, 65, 80, 70, 95, 110],
                borderColor: colors.enroll,
                backgroundColor: colors.enroll,
                tension: 0.4,
                fill: true,
                pointRadius: 5
            }]
        },
        options: {
            responsive: true,
            plugins: { legend: { display: true } },
            scales: { y: { beginAtZero: true } }
        }
    });
});
