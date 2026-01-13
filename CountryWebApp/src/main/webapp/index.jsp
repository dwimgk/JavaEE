<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>CountryWeb 🗺️</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <!-- Bootstrap CSS -->
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
        rel="stylesheet">
</head>

<body>
    <h2 class="text-center mt-4">
        Get basic information about countries!
    </h2>

    <div class="shadow p-3 mb-5 bg-body rounded container-sm mt-4">
        <table class="table table-sm table-striped table-bordered table-hover table-light">
            <thead class="table-dark">
                <tr>
                    <th>#</th>
                    <th>Country Name</th>
                    <th>Original Name</th>
                    <th>Capital</th>
                    <th>TLD</th>
                    <th>Flag</th>
                    <th>CoA</th>
                    <th>Map</th>
                </tr>
            </thead>
            <tbody id="countryTableBody">
                <tr>
                    <td>1</td>
                    <td>Example</td>
                    <td>Example</td>
                    <td>Example</td>
                    <td>.ex</td>
                    <td>Example</td>
                    <td>Example</td>
                    <td>Example</td>
                </tr>
            </tbody>
        </table>
    </div>
</body>
</html>
