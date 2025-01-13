import React from "react";
import { Box, TextField, Button, Typography } from "@mui/material";

const Register = () => {
  const handleSubmit = (event) => {
    event.preventDefault();
    // Handle registration logic
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
        <Typography variant="h5" component="h1" textAlign="center" gutterBottom>
          Register
        </Typography>
        <form onSubmit={handleSubmit}>
          <TextField
            fullWidth
            margin="normal"
            label="Username"
            variant="outlined"
            required
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
