<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../../css/common/navbar.css">
    <title>제목</title>
    <script src="https://kit.fontawesome.com/670206db20.js" crossorigin="anonymous"></script>

</head>
<body>


    <nav>
        <div></div>
        <div>
            <a href="">
                <div>
                    단어충전소
                </div>
            </a>
        </div>
        <div>
            <div id="project-text">
                <div>Automate</div>
                <div>English</div>
                <div style="color:#ffb50a;">Vocabulary</div>
                <div style="color:#ffb50a;">Memorization</div>
            </div>

        </div>
        <div>
            <button onclick="toggleDropdown('aboutDropdown')">
                ABOUT
            </button>
        </div>
        <div>
            <button>
                <i class="fa-solid fa-dollar-sign"></i>
            </button>
        </div>
        <div>
            <button>
                CHARGER
            </button>
        </div>
        <div>
            <button>
                BOARD
            </button>
        </div>
        <div>
            <button>
                FAQ
            </button>
        </div>
        <div></div>
        <div>
            <form action="">
                <input type="" placeholder="영단어 찾기">

        </div>
        <div>
            <button type="submit">search</button>
            </form>
        </div>
        <div></div>

    </nav>

    <!-- under-navbar-first -->
    <div class="under-navbar-first">
        <div id="aboutDropdown" class="dropdown-content">
            <p>About content here...</p>
        </div>
    </div>

    <script>
        function toggleDropdown(dropdownId) {
            var dropdown = document.getElementById(dropdownId);
            dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
        }

    </script>




</body>
</html>
