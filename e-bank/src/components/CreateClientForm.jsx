import React, { useState, useContext } from "react";
import { AuthContext } from "../context/AuthContext";

export default function CreateClientForm() {
  const { token } = useContext(AuthContext);

  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    identityNumber: "",
    birthDate: "",
    email: "",
    postalAddress: "",
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

    if (
      !form.firstName ||
      !form.lastName ||
      !form.identityNumber ||
      !form.birthDate ||
      !form.email ||
      !form.postalAddress
    ) {
      setError("Veuillez remplir tous les champs.");
      return;
    }

    try {
      const response = await fetch(`${API_BASE_URL}/api/clients/add`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(form),
      });

      if (!response.ok) {
        const message = await response.text();
        setError(message || "Erreur lors de la création du client.");
        return;
      }

      setSuccess("Client créé avec succès !");
      setForm({
        firstName: "",
        lastName: "",
        identityNumber: "",
        birthDate: "",
        email: "",
        postalAddress: "",
      });
    } catch (err) {
      setError("Erreur réseau. Veuillez réessayer.");
    }
  };

  return (
    <div className="bg-white shadow-md rounded-md p-6">
      <h1 className="text-2xl font-bold mb-6 text-gray-800">Créer un nouveau client</h1>

      <form onSubmit={handleSubmit} className="space-y-4">
        {error && <div className="bg-red-100 text-red-700 p-2 rounded">{error}</div>}
        {success && <div className="bg-green-100 text-green-700 p-2 rounded">{success}</div>}

        {[
          { label: "Prénom", name: "firstName", type: "text" },
          { label: "Nom", name: "lastName", type: "text" },
          { label: "Numéro d'identité", name: "identityNumber", type: "text" },
          { label: "Date de naissance", name: "birthDate", type: "date" },
          { label: "Email", name: "email", type: "email" },
          { label: "Adresse postale", name: "postalAddress", type: "text" },
        ].map(({ label, name, type }) => (
          <div key={name}>
            <label className="block font-semibold mb-1">{label}</label>
            <input
              type={type}
              name={name}
              value={form[name]}
              onChange={handleChange}
              className="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring focus:ring-blue-400"
              placeholder={label}
            />
          </div>
        ))}

        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition"
        >
          Créer le client
        </button>
      </form>
    </div>
  );
}
