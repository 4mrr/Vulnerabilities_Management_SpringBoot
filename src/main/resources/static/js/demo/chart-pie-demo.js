// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

// Pie Chart Example
var plaAndroid = document.getElementById("plaAndroid");
let plaAndroid_value = plaAndroid.textContent;
var plaPhp = document.getElementById("plaPhp");
let plaPhp_value = plaPhp.textContent;
var plaWindows = document.getElementById("plaWindows");
let plaWindows_value = plaWindows.textContent;
var plaLinux = document.getElementById("plaLinux");
let plaLinux_value = plaLinux.textContent;
var plaHardware = document.getElementById("plaHardware");
let plaHardware_value = plaHardware.textContent;
var plaIos = document.getElementById("plaIos");
let plaIos_value = plaIos.textContent;
var plaJava = document.getElementById("plaJava");
let plaJava_value = plaJava.textContent;

var totalVuln = document.getElementById("totalVuln");
let totalVuln_value = totalVuln.textContent;

let data_android = (plaAndroid_value / totalVuln_value)*100;
let data_php = (plaPhp_value / totalVuln_value)*100;
let data_ios = (plaIos_value / totalVuln_value)*100;
let data_linux = (plaLinux_value / totalVuln_value)*100;
let data_windows = (plaWindows_value / totalVuln_value)*100;
let data_java = (plaJava_value / totalVuln_value)*100;
let data_hardware = (plaHardware_value / totalVuln_value)*100;

var ctx = document.getElementById("myPieChart");
var myPieChart = new Chart(ctx, {
  type: 'doughnut',
  options: {
    maintainAspectRatio: false,
    tooltips: {
      backgroundColor: "rgb(255,255,255)",
      bodyFontColor: "#858796",
      borderColor: '#dddfeb',
      borderWidth: 1,
      xPadding: 15,
      yPadding: 15,
      displayColors: false,
      caretPadding: 10,
    },
    legend: {
      display: false
    },
    cutoutPercentage: 80
  },
  data: {
    labels: ["Linux", "Windows", "Hardware", "PHP","JAVA","IOS","Android"],
    datasets: [{
      data: [data_linux, data_windows, data_hardware,data_php,data_java,data_ios,data_android],
      backgroundColor: ['#e74a3b', '#f6c23e', '#36b9cc','#858796' , '#4e73df', '#1cc88a' ,'#5a5c69'],
      hoverBackgroundColor: ['#2e59d9', '#17a673', '#2c9faf'],
      hoverBorderColor: "rgba(234, 236, 244, 1)",
    }],
  },
});
