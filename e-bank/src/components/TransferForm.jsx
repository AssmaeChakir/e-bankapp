/* eslint-disable no-unused-vars */
import React, { useState, useEffect } from "react";

export default function TransferForm({ accounts, selectedAccount, token }) {
  const [formData, setFormData] = useState({
    fromRib: selectedAccount?.rib || "",
    toRib: "",
    amount: "",
    motif: "",
  });
  const [message, setMessage] = useState("");

  useEffect(() => {
    setFormData((prev) => ({
      ...prev,
      fromRib: selectedAccount?.rib || "",
    }));
  }, [selectedAccount]);

  const handleChange = (e) => {
    setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");

    try {
      const res = await fetch("http://localhost:8082/api/accounts/transfer", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(formData),
      });

      if (!res.ok) {
        const error = await res.text();
        throw new Error(error);
      }

      setMessage("✅ Virement effectué avec succès.");
    } catch (err) {
      setMessage(`❌ Solde insuffisant pour effectuer le virement`);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="mt-6 space-y-4 bg-white p-6 rounded shadow">
      <h3 className="text-lg font-semibold">Effectuer un virement</h3>

      <div>
        <label className="block font-medium">Depuis (RIB):</label>
        <select
          name="fromRib"
          value={formData.fromRib}
          onChange={handleChange}
          className="w-full border p-2 rounded"
        >
          {accounts.map((acc) => (
            <option key={acc.rib} value={acc.rib}>
              {acc.rib} ({acc.balance.toFixed(2)} MAD)
            </option>
          ))}
        </select>
      </div>

      <div>
        <label className="block font-medium">RIB Destinataire:</label>
        <input
          type="text"
          name="toRib"
          value={formData.toRib}
          onChange={handleChange}
          className="w-full border p-2 rounded"
          required
        />
      </div>

      <div>
        <label className="block font-medium">Montant:</label>
        <input
          type="number"
          name="amount"
          value={formData.amount}
          onChange={handleChange}
          className="w-full border p-2 rounded"
          required
        />
      </div>

      <div>
        <label className="block font-medium">Motif:</label>
        <input
          type="text"
          name="motif"
          value={formData.motif}
          onChange={handleChange}
          className="w-full border p-2 rounded"
        />
      </div>

      <button
        type="submit"
        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
      >
        Valider le virement
      </button>

      {message && <p className="text-sm text-red-600 mt-2">{message}</p>}
    </form>
  );
}
