import React, { useState, useEffect } from "react";
import axios from "axios";
import { Box, Typography, Alert, CircularProgress, Button } from "@mui/material";
import { useParams } from "react-router-dom"; 

const ProfilePage = () => {
  const { username } = useParams();  // Get the username from the URL parameter
  const [user, setUser] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUserProfile = async () => {
      const token = localStorage.getItem("token"); // Retrieve the token from localStorage

      if (!token) {
        setError("You must be logged in to view this page.");
        setLoading(false);
        return;
      }

      try {
        const response = await axios.get(`http://localhost:8080/api/users/${username}`, {
          headers: {
            Authorization: `Bearer ${token}`, // Send the token in the Authorization header
          },
        });
        setUser(response.data.data);  // Set user data from the response
      } catch (err) {
        setError("User profile could not be fetched.");
      } finally {
        setLoading(false);
      }
    };

    fetchUserProfile();
  }, [username]); // Re-fetch the profile if the username changes

  if (loading) {
    return (
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          height: "100vh",
          backgroundColor: "#121212",
        }}
      >
        <CircularProgress color="primary" />
      </Box>
    );
  }

  if (error) {
    return (
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          height: "100vh",
          backgroundColor: "#121212",
        }}
      >
        <Alert severity="error">{error}</Alert>
      </Box>
    );
  }

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
        <Typography variant="h5" component="h1" textAlign="center" gutterBottom>
          Profile
        </Typography>
        <Typography variant="h6" textAlign="center" color="textSecondary">
          Username: {user.username}
        </Typography>

        <Box sx={{ marginTop: 2 }}>
          <Button
            fullWidth
            variant="contained"
            sx={{
              backgroundColor: "#1976d2",
              "&:hover": {
                backgroundColor: "#115293",
              },
            }}
            onClick={() => alert("Edit Profile Clicked")}
          >
            Edit Profile
          </Button>
        </Box>
      </Box>
    </Box>
  );
};

export default ProfilePage;
