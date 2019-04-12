using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using BookApi.Models;

namespace BookApi.Controllers
{
    public class PaniersController : ApiController
    {
        private BookApiContext db = new BookApiContext();

        // GET: api/Paniers
        public IQueryable<Panier> GetPaniers()
        {
            return db.Paniers;
        }

        // GET: api/Paniers/5
        [ResponseType(typeof(Panier))]
        public async Task<IHttpActionResult> GetPanier(int id)
        {
            Panier panier = await db.Paniers.FindAsync(id);
            if (panier == null)
            {
                return NotFound();
            }

            return Ok(panier);
        }

        // PUT: api/Paniers/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> PutPanier(int id, Panier panier)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != panier.Id)
            {
                return BadRequest();
            }

            db.Entry(panier).State = EntityState.Modified;

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PanierExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Paniers
        [ResponseType(typeof(Panier))]
        public async Task<IHttpActionResult> PostPanier(Panier panier)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Paniers.Add(panier);
            await db.SaveChangesAsync();

            return CreatedAtRoute("DefaultApi", new { id = panier.Id }, panier);
        }

        // DELETE: api/Paniers/5
        [ResponseType(typeof(Panier))]
        public async Task<IHttpActionResult> DeletePanier(int id)
        {
            Panier panier = await db.Paniers.FindAsync(id);
            if (panier == null)
            {
                return NotFound();
            }

            db.Paniers.Remove(panier);
            await db.SaveChangesAsync();

            return Ok(panier);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool PanierExists(int id)
        {
            return db.Paniers.Count(e => e.Id == id) > 0;
        }
    }
}