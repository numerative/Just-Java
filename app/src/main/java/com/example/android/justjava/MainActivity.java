/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */
package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.name;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     * Here the @calculatePrice method is called.
     */
    public void submitOrder(View view) {
        createOrderSummary();
    }

    public void increment(View view) {
        if (quantity > 99) {
            Context context = getApplicationContext();
            CharSequence text = "Whoa! That's a lot of Coffee!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            quantity += 1;
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity < 1) {
            Context context = getApplicationContext();
            CharSequence text = "Come on! Don't be shy.. Order something...";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            quantity -= 1;
            displayQuantity(quantity);
        }
    }

    /**
     * Calculates the price of the order.
     * @return total price.
     * update: the below method will be replaced by createOrderSummary method.
     *
     *  private int calculatePrice() {
     *  int price = quantity * 5;
     *  return price;
     * }
     */

    /**
     * This method is expected to return a string spanning multiple lines.
     * It is expected to display Quantity, Total Price and some text lines.
     */
    public String createOrderSummary() {
        int whippedPrice = 0;
        int chocolatePrice = 0;

        //gets the text Entered in the EditText field
        final EditText nameBox = (EditText) findViewById(R.id.name_field);
        String customerName = nameBox.getText().toString();

        //figure out whether customer needs whipped cream?
        final CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.has_w_cream);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //figure out whether customer needs Chocolate?
        final CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.has_chocolate);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //Finding out if WhippedCream added and then calculating its Charge in the order
        if (hasWhippedCream) {
            whippedPrice = 1 * quantity;
        }

        //Finding out if WhippedCream added and then calculating its Charge in the order
        if (hasChocolate) {
            chocolatePrice = 2 * quantity;
        }

        //Calculates the Price
        int price = quantity * 5;
        price += whippedPrice + chocolatePrice;

        //Prints the Order Summary
        String displayMessage = getString(R.string.order_summary_name, customerName);
        displayMessage += "\n" + getString(R.string.order_summary_whipped_cream, hasWhippedCream);
        displayMessage += "\n" + getString(R.string.order_summary_chocolate, hasChocolate);
        displayMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        displayMessage += "\n" + getString(R.string.order_summary_price, price);
        displayMessage += "\n" + getString(R.string.thank_you);

        displayMessage(displayMessage);

        //Intents to sending an email
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order from " + name);
        intent.putExtra(Intent.EXTRA_TEXT, displayMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        return displayMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView OrderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        OrderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView OrderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        OrderSummaryTextView.setText(message);
    }
}