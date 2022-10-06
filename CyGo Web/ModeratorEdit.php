<?php
include("config.php");
include("firebaseRDB.php");

$db = new firebaseRDB($databaseURL);
$id = $_GET['id'];
$retrieve = $db->retrieve("userModerator/$id");
$data = json_decode($retrieve, 1);

?>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>CYGO - Add Admin</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link rel="icon" type="image/png" href="assets/img/LOGO.png">

  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css?family=Montserrat:300,400,500,700|Open+Sans:300,300i,400,400i,700,700i"
    rel="stylesheet">


  <!-- Vendor CSS Files -->
  <link href="assets/vendor/aos/aos.css" rel="stylesheet">
  <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="assets/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link href="assets/css/style.css" rel="stylesheet">
  <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

</head>

<body>

      <!-- ======= Header ======= -->
      <header id="header" class="fixed-top d-flex align-items-center header-transparent">
    <div class="container d-flex justify-content-between align-items-center">

      <div id="logo">
        <h1><a href="index.html">CYGO</a></h1>

      </div>

      <nav id="navbar" class="navbar">
        <ul>
          <li><a class="nav-link scrollto active" href="ModeratorEdit.php">Edit Moderater</a></li>
          <li><a class="nav-link scrollto" href="ManageModerator.php">Back</a></li>

      </nav><!-- .navbar -->
    </div>
  </header><!-- End Header -->

  <!-- ======= Hero Section ======= -->
  <section id="hero">

    <section class="vh-100">
      <div class="container py-5 h-100">
        <div class="row d-flex align-items-center justify-content-center h-100">
          <div class="col-md-9 col-lg-6 col-xl-5">
            <div class="col-lg-6 about-img" data-aos="fade-right" dat-aos-delay="100">
              <img src="assets/img/moderator.svg" alt="">
            </div>
          </div>
          <div class="col-md-7 col-lg-5 col-xl-5 offset-xl-1">

            <div class="container">

            <form method="post" action="Moderatoraction_edit.php">

            <h2 class="alert" > Edit Moderator  </h2><br>
              
                  <tr>
                     <td>Edit position</td>
                     <td>:</td>
                     <td><input type="text" class="form-control form-control-lg"  name="position" value="<?php echo $data['position']?>"></td>
                  </tr>
                  <tr>
                  <br>  <td>Edit Username</td>
                     <td>:</td>
                     <td><input type="text" class="form-control form-control-lg"  name="email" value="<?php echo $data['email']?>"></td>
                  </tr>
                  <tr>
                  <br>   <td>Edit Password</td>
                     <td>:</td>
                     <td><input type="password" class="form-control form-control-lg"  name="password" value="<?php echo $data['password']?>"></td>
                  </tr>
                  <tr>
                  <br>  <td>
                        <input type="hidden"  name="id" value="<?php echo $id?>">
                        <input type="submit" class="form-control form-control-lg , btn btn-primary btn btn-block"  value="SAVE">
                     </td>
                  </tr>
                   
            </form>

              <script src="https://cdnjs.cloudflare.com/ajax/libs/firebase/7.14.1-0/firebase.js"></script>
              <script src="./moderator.js"></script>

            </div>


    </section><!-- End Hero Section -->


    <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
        class="bi bi-chevron-up"></i></a>

    <!-- Vendor JS Files -->
    <script src="assets/vendor/aos/aos.js"></script>
    <script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="assets/vendor/glightbox/js/glightbox.min.js"></script>
    <script src="assets/vendor/php-email-form/validate.js"></script>

    <!-- Template Main JS File -->
    <script src="assets/js/main.js"></script>

</body>

</html>

