import React from "react";
import { Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import AgentDashboard from "./pages/AgentDashboard";
import { AuthProvider } from "./context/AuthContext"; // adjust path as needed
import './App.css'
import ClientDashboard from "./pages/ClientDashboard";

function App() {
  return (
    <AuthProvider>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/agent-dashboard" element={<AgentDashboard />} />
        <Route path="/client-dashboard" element={<ClientDashboard />} />
        {/* add other routes here */}
      </Routes>
    </AuthProvider>
  );
}

export default App;
