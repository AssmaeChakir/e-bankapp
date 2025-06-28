import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import {jwtDecode} from "jwt-decode"; // keep your import style as requested
import { AuthContext } from "../context/AuthContext"; // adjust path as needed

const API_BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8082";

export default function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const { login } = useContext(AuthContext);

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      const response = await fetch(`${API_BASE_URL}/auth/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        if (response.status === 401) {
          throw new Error("Nom d'utilisateur ou mot de passe incorrect");
        }
        throw new Error("Erreur lors de la connexion");
      }

      const data = await response.json();
      const token = data.token;

      // Save token in context and localStorage via login method
      login(token);

      const decoded = jwtDecode(token);
      console.log("Decoded JWT:", decoded);

      const roles = decoded.authorities || decoded.roles || [];
      console.log("Roles:", roles);

      if (roles.includes("ROLE_AGENT_GUICHET")) {
        navigate("/agent-dashboard");
      } else if (roles.includes("ROLE_CLIENT")) {
        navigate("/client-dashboard");
      } else {
        navigate("/");
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <form
        onSubmit={handleLogin}
        className="bg-white shadow-md rounded-lg px-8 py-6 w-full max-w-md"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">Connexion</h2>

        {error && (
          <div className="bg-red-100 text-red-700 px-4 py-2 rounded mb-4">
            {error}
          </div>
        )}

        <div className="mb-4">
          <label className="block text-gray-700">Nom d'utilisateur</label>
          <input
            type="text"
            className="mt-1 block w-full px-4 py-2 border rounded-md focus:ring focus:ring-blue-200"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>

        <div className="mb-6">
          <label className="block text-gray-700">Mot de passe</label>
          <input
            type="password"
            className="mt-1 block w-full px-4 py-2 border rounded-md focus:ring focus:ring-blue-200"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>

        <button
          type="submit"
          disabled={loading}
          className="w-full bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-700 transition disabled:opacity-50"
        >
          {loading ? "Connexion..." : "Se connecter"}
        </button>
      </form>
    </div>
  );
}
