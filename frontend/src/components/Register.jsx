import React, { useState } from "react";
import axios from "axios";
import { Box, TextField, Button, Typography, Alert } from "@mui/material";
import logo from "../assets/logo.png"; 

const Register = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [message, setMessage] = useState(null);
  const [error, setError] = useState(null);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setMessage(null);
    setError(null);

    if (password !== confirmPassword) {
      setError("Passwords do not match!");
      return;
    }

    try {
      const response = await axios.post("http://localhost:8080/register", {
        username,
        password,
      });

      setMessage("Registration successful!");
      setUsername("");
      setPassword("");
      setConfirmPassword("");
    } catch (error) {
      setError(
        error.response?.data?.message || "An error occurred. Please try again."
      );
    }
  };

  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
        backgroundColor: "#121212", // Dark background
        color: "#ffffff",
      }}
    >
      <Box
        sx={{
          width: 300,
          padding: 3,
          borderRadius: 2,
          backgroundColor: "#1e1e1e", // Slightly lighter dark background for the form
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
          Register
        </Typography>
        {message && <Alert severity="success">{message}</Alert>}
        {error && <Alert severity="error">{error}</Alert>}
        <form onSubmit={handleSubmit}>
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
          <TextField
            fullWidth
            margin="normal"
            label="Confirm Password"
            type="password"
            variant="outlined"
            required
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
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
            Register
          </Button>
        </form>
      </Box>
    </Box>
  );
};

export default Register;
