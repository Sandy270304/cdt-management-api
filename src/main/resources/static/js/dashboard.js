const advisorId = localStorage.getItem("advisorId");
const advisorUsername = localStorage.getItem("advisorUsername");
const advisorFullName = localStorage.getItem("advisorFullName");

const advisorInfo = document.getElementById("advisorInfo");
const logoutButton = document.getElementById("logoutButton");
const refreshButton = document.getElementById("refreshButton");
const generateNotificationsButton = document.getElementById("generateNotificationsButton");

const customerCount = document.getElementById("customerCount");
const termDepositCount = document.getElementById("termDepositCount");
const pendingNotificationCount = document.getElementById("pendingNotificationCount");

const notificationsContainer = document.getElementById("notificationsContainer");
const maturingDepositsContainer = document.getElementById("maturingDepositsContainer");

if (!advisorId) {
    window.location.href = "index.html";
}

advisorInfo.textContent = `${advisorFullName} (${advisorUsername})`;

logoutButton.addEventListener("click", function () {
    localStorage.clear();
    window.location.href = "index.html";
});

refreshButton.addEventListener("click", loadDashboard);

generateNotificationsButton.addEventListener("click", async function () {
    try {
        await apiPost(`/notifications/generate/advisor/${advisorId}`);
        await loadDashboard();
        alert("Notifications generated successfully.");
    } catch (error) {
        alert("Could not generate notifications. Check backend console.");
    }
});

async function loadDashboard() {
    try {
        const customers = await apiGet("/customers");
        const termDeposits = await apiGet("/term-deposits");
        const pendingNotifications = await apiGet(`/notifications/advisor/${advisorId}/pending`);
        const maturingDeposits = await apiGet("/term-deposits/maturing/next-five-days");

        customerCount.textContent = customers.length;
        termDepositCount.textContent = termDeposits.length;
        pendingNotificationCount.textContent = pendingNotifications.length;

        renderNotifications(pendingNotifications);
        renderMaturingDeposits(maturingDeposits);
    } catch (error) {
        console.error(error);
        alert("Could not load dashboard data. Check backend console.");
    }
}

function renderNotifications(notifications) {
    if (!notifications || notifications.length === 0) {
        notificationsContainer.innerHTML = "<p>No pending notifications found.</p>";
        return;
    }

    let html = `
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Customer</th>
                    <th>Certificate</th>
                    <th>Maturity Date</th>
                    <th>Days Remaining</th>
                    <th>Priority</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
    `;

    notifications.forEach(notification => {
        const termDeposit = notification.termDeposit;
        const customer = termDeposit.customer;

        html += `
            <tr>
                <td>${notification.id}</td>
                <td>${customer.firstName} ${customer.lastName}</td>
                <td>${termDeposit.certificateNumber}</td>
                <td>${termDeposit.maturityDate}</td>
                <td>${notification.daysRemaining}</td>
                <td>${renderPriorityBadge(notification.priority)}</td>
                <td>${notification.status}</td>
                <td>
                    <button class="small-button" onclick="markAsContacted(${notification.id})">Contacted</button>
                    <button class="small-button" onclick="markAsRenewed(${notification.id})">Renewed</button>
                </td>
            </tr>
        `;
    });

    html += `
            </tbody>
        </table>
    `;

    notificationsContainer.innerHTML = html;
}

function renderMaturingDeposits(termDeposits) {
    if (!termDeposits || termDeposits.length === 0) {
        maturingDepositsContainer.innerHTML = "<p>No active term deposits maturing in the next five days.</p>";
        return;
    }

    let html = `
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Customer</th>
                    <th>Certificate</th>
                    <th>Principal Amount</th>
                    <th>Interest Rate</th>
                    <th>Maturity Date</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
    `;

    termDeposits.forEach(termDeposit => {
        const customer = termDeposit.customer;

        html += `
            <tr>
                <td>${termDeposit.id}</td>
                <td>${customer.firstName} ${customer.lastName}</td>
                <td>${termDeposit.certificateNumber}</td>
                <td>${formatCurrency(termDeposit.principalAmount)}</td>
                <td>${termDeposit.annualInterestRate}%</td>
                <td>${termDeposit.maturityDate}</td>
                <td>${termDeposit.status}</td>
            </tr>
        `;
    });

    html += `
            </tbody>
        </table>
    `;

    maturingDepositsContainer.innerHTML = html;
}

function renderPriorityBadge(priority) {
    const normalizedPriority = String(priority).toLowerCase();

    return `<span class="badge badge-${normalizedPriority}">${priority}</span>`;
}

function formatCurrency(value) {
    return new Intl.NumberFormat("en-US", {
        style: "currency",
        currency: "COP",
        minimumFractionDigits: 0
    }).format(value);
}

async function markAsContacted(notificationId) {
    try {
        await apiPut(`/notifications/${notificationId}/manage`, {
            status: "CONTACTED",
            notes: "Customer contacted from dashboard."
        });

        await loadDashboard();
    } catch (error) {
        alert("Could not update notification.");
    }
}

async function markAsRenewed(notificationId) {
    try {
        await apiPut(`/notifications/${notificationId}/manage`, {
            status: "RENEWED",
            notes: "Customer accepted renewal from dashboard."
        });

        await loadDashboard();
    } catch (error) {
        alert("Could not renew notification.");
    }
}

loadDashboard();