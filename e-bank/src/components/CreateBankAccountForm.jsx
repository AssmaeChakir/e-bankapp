import React, { useState, useContext } from "react";
import { AuthContext } from "../context/AuthContext";

export default function CreateBankAccountForm() {
  const { token } = useContext(AuthContext);

  const [form, setForm] = useState({
    rib: "",
    identityNumber: "",
  });

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const API_BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8082";

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");

    if (!form.rib || !form.identityNumber) {
      setError("Veuillez remplir tous les champs.");
      return;
    }

    try {
      const response = await fetch(`${API_BASE_URL}/api/accounts/create`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(form),
      });

      if (!response.ok) {
        const message = await response.text();
        setError(message || "Erreur lors de la création du compte bancaire.");
        return;
      }

      setSuccess("Compte bancaire créé avec succès !");
      setForm({
        rib: "",
        identityNumber: "",
      });
    } catch (err) {
      setError("Erreur réseau. Veuillez réessayer.");
    }
  };

  return (
    <div className="bg-white shadow-md rounded-md p-6 max-w-md mx-auto">
      <h1 className="text-2xl font-bold mb-6 text-gray-800">Créer un nouveau compte bancaire</h1>

      <form onSubmit={handleSubmit} className="space-y-4">
        {error && <div className="bg-red-100 text-red-700 p-2 rounded">{error}</div>}
        {success && <div className="bg-green-100 text-green-700 p-2 rounded">{success}</div>}

        <div>
          <label className="block font-semibold mb-1">RIB</label>
          <input
            type="text"
            name="rib"
            value={form.rib}
            onChange={handleChange}
            className="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring focus:ring-blue-400"
            placeholder="RIB"
          />
        </div>

        <div>
          <label className="block font-semibold mb-1">Numéro d'identité du client</label>
          <input
            type="text"
            name="identityNumber"
            value={form.identityNumber}
            onChange={handleChange}
            className="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring focus:ring-blue-400"
            placeholder="Numéro d'identité"
          />
        </div>

        <button
          type="submit"
          className="w-full bg-green-600 text-white py-2 rounded hover:bg-green-700 transition"
        >
          Créer le compte bancaire
        </button>
      </form>
    </div>
  );
}
