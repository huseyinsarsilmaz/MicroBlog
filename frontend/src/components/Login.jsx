import React, { useState } from "react";
import axios from "axios";
import { Box, TextField, Button, Typography, Snackbar } from "@mui/material";
import logo from "../assets/logo.png";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (event) => {
    event.preventDefault();
    setSnackbarMessage(""); // Reset previous message

    try {
      const response = await axios.post("http://localhost:8080/api/login", {
        username,
        password,
      });
      console.log(response);

      localStorage.setItem("token", response.data.data.token);
      localStorage.setItem("username", username);

      setSnackbarMessage("Login successful! Redirecting to profile...");
      setOpenSnackbar(true);
      setTimeout(() => {
        navigate(`/profile/${username}`);
      }, 1500); // Redirect after a short delay to show the success message
    } catch (error) {
      setSnackbarMessage("Login failed. Please try again.");
      setOpenSnackbar(true);
      setTimeout(() => {
        navigate("/login"); // Redirect to login page after an error
      }, 1500);
    }
  };

  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
        backgroundColor: "#121212",
        color: "#ffffff",
      }}
    >
      <Box
        sx={{
          width: 300,
          padding: 3,
          borderRadius: 2,
          backgroundColor: "#1e1e1e",
          boxShadow: "0px 4px 10px rgba(0,0,0,0.5)",
        }}
      >
        <Box sx={{ textAlign: "center", marginBottom: 2 }}>
          <img
            src={logo}
            alt="Logo"
            style={{ width: "100px", height: "auto" }}
          />
        </Box>
        <Typography variant="h5" component="h1" textAlign="center" gutterBottom>
          Login
        </Typography>

        <form onSubmit={handleLogin}>
          <TextField
            fullWidth
            margin="normal"
            label="Username"
            variant="outlined"
            required
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            InputLabelProps={{ style: { color: "#888" } }}
            InputProps={{
              style: {
                color: "#fff",
                backgroundColor: "#333",
              },
            }}
          />
          <TextField
            fullWidth
            margin="normal"
            label="Password"
            type="password"
            variant="outlined"
            required
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            InputLabelProps={{ style: { color: "#888" } }}
            InputProps={{
              style: {
                color: "#fff",
                backgroundColor: "#333",
              },
            }}
          />
          <Button
            fullWidth
            variant="contained"
            type="submit"
            sx={{
              marginTop: 2,
              backgroundColor: "#1976d2",
              "&:hover": {
                backgroundColor: "#115293",
              },
            }}
          >
            Login
          </Button>
        </form>
      </Box>

      {/* Snackbar for login feedback */}
      <Snackbar
        open={openSnackbar}
        autoHideDuration={3000}
        onClose={() => setOpenSnackbar(false)}
        message={snackbarMessage}
      />
    </Box>
  );
};

export default Login;
