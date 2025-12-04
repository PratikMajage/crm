const toggleBtn = document.getElementById("themeToggle");

// ✅ Check saved mode on page load
if (localStorage.getItem("theme") === "dark") {
    document.body.classList.add("dark-mode");
    toggleBtn.innerHTML = '<i class="bi bi-brightness-high"></i> Light Mode';
}

toggleBtn.addEventListener("click", function () {
    document.body.classList.toggle("dark-mode");

    if (document.body.classList.contains("dark-mode")) {
        toggleBtn.innerHTML = '<i class="bi bi-brightness-high"></i> Light Mode';
        localStorage.setItem("theme", "dark");  // ✅ Save preference
    } else {
        toggleBtn.innerHTML = '<i class="bi bi-moon"></i> Dark Mode';
        localStorage.setItem("theme", "light"); // ✅ Save preference
    }
});