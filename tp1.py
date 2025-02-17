import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# Read the CSV file
df = pd.read_csv("test_results.csv")

# Create a FacetGrid based on Threads and Operations per Thread
# Each subplot will plot the Avg value versus Probability Add and use a different color for each Test.
g = sns.FacetGrid(df, row="Threads", col="Operations per Thread", hue="Test",
                  margin_titles=True, sharey=False, height=4, aspect=1.5)

g.map(plt.plot, "Probability Add", "Avg", marker="o")
g.set_axis_labels("Probability Add", "Avg (seconds)")
g.add_legend()

plt.tight_layout()
plt.show()